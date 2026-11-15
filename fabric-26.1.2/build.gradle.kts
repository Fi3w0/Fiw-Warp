import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("net.fabricmc.fabric-loom")
	kotlin("jvm")
}

val modId = providers.gradleProperty("mod_id").get()


val targetProperties = Properties().apply {
	val propertiesFile = file("gradle.properties")
	if (propertiesFile.exists()) propertiesFile.inputStream().use { load(it) }
}
fun target(name: String): String = targetProperties.getProperty(name) ?: providers.gradleProperty(name).get()
val minecraftVersion = target("minecraft_version")
val javaVersion = target("java_version")
val commonPath = if (minecraftVersion == "1.21.11") ":common" else ":common-$minecraftVersion"

base { archivesName.set("${providers.gradleProperty("archives_name").get()}-fabric-$minecraftVersion") }

evaluationDependsOn(commonPath)
val commonMain = project(commonPath).extensions.getByType<SourceSetContainer>()["main"]

loom {
	mods {
		register(modId) {
			sourceSet(sourceSets.main.get())
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${minecraftVersion}")
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")
	implementation("net.fabricmc.fabric-api:fabric-api:${target("fabric_api_version")}")
	implementation("net.fabricmc:fabric-language-kotlin:${providers.gradleProperty("fabric_kotlin_version").get()}")

	// Shared code from :common (compiled against Mojang mappings, remapped on build).
	compileOnly(commonMain.output)
}

tasks.named<Jar>("jar") {
	from(commonMain.output)
}

tasks.named<ProcessResources>("processResources") {
	val props = mapOf("version" to project.version, "minecraft_version" to minecraftVersion, "java_version" to javaVersion)
	inputs.properties(props)
	filesMatching("fabric.mod.json") { expand(props) }
}

java {
	withSourcesJar()
	sourceCompatibility = JavaVersion.toVersion(javaVersion)
	targetCompatibility = JavaVersion.toVersion(javaVersion)
}

tasks.withType<JavaCompile>().configureEach { options.release.set(javaVersion.toInt()) }

kotlin {
	compilerOptions { jvmTarget.set(JvmTarget.fromTarget(javaVersion)) }
}
