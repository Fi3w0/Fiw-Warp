plugins {
	id("net.fabricmc.fabric-loom-remap") version "1.16-SNAPSHOT" apply false
	id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT" apply false
	id("net.neoforged.moddev") version "2.0.141" apply false
	id("net.neoforged.moddev.legacyforge") version "2.0.141" apply false
	kotlin("jvm") version "2.4.0" apply false
}

allprojects {
	group = providers.gradleProperty("maven_group").get()
	version = providers.gradleProperty("mod_version").get()

	repositories {
		mavenCentral()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		maven("https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
	}
}

tasks.register<Exec>("smokeTest") {
	val module = providers.gradleProperty("smokeTarget").getOrElse("fabric-26.1.2")
	dependsOn("$module:build")
	val artifactModule = if (module == "fabric" || module == "neoforge") "$module-1.21.11" else module
	commandLine("python3", ".github/scripts/smoke-test.py", module,
		"$module/build/libs/fiw-warp-$artifactModule-${project.version}.jar")
	providers.gradleProperty("smokeSuiteFile").orNull?.let { args("--suite-file", it) }
	providers.gradleProperty("smokeExtraJars").orNull?.split("|")?.forEach { args("--extra-jar", it) }
	providers.gradleProperty("smokeCommands").orNull?.split("|")?.forEach { args("--command", it) }
	providers.gradleProperty("smokeExpected").orNull?.split("|")?.forEach { args("--expect", it) }
}

subprojects {
	plugins.withId("org.jetbrains.kotlin.jvm") {
		extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
			compilerOptions {
				apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
				languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
			}
		}
	}
}


tasks.register<Exec>("installPlayerTestTools") {
    inputs.files(".github/player-tests/package.json", ".github/player-tests/package-lock.json")
    outputs.dir(".github/player-tests/node_modules")
    commandLine("npm", "ci", "--ignore-scripts", "--no-audit", "--no-fund",
        "--cache", "build/npm-cache", "--prefix", ".github/player-tests")
}


tasks.register<Exec>("playerTest") {
    val module = providers.gradleProperty("smokeTarget").getOrElse("fabric-26.1.2")
    dependsOn("$module:build", "installPlayerTestTools")
    val artifactModule = if (module == "fabric" || module == "neoforge") "$module-1.21.11" else module
    commandLine("python3", ".github/scripts/smoke-test.py", module,
        "$module/build/libs/fiw-warp-$artifactModule-${project.version}.jar",
        "--player-script", ".github/player-tests/gameplay.cjs", "--player-phases", "first,restart")
}
