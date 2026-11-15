import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("net.neoforged.moddev.legacyforge")
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

base { archivesName.set("${providers.gradleProperty("archives_name").get()}-forge-$minecraftVersion") }

evaluationDependsOn(commonPath)
val commonMain = project(commonPath).extensions.getByType<SourceSetContainer>()["main"]

val forgeVersion = "$minecraftVersion-${target("forge_version")}"

legacyForge {
	version = forgeVersion

	runs {
		register("server") {
			server()
		}
	}

	mods {
		register(modId) {
			sourceSet(sourceSets.main.get())
		}
	}
}

dependencies {
	// Kotlin runtime + @Mod language adapter for NeoForge.
	implementation("thedarkcolour:kotlinforforge:${target("kotlin_for_forge_version")}")

	// Shared code from :common (same Mojang mappings as NeoForge runtime).
	compileOnly(commonMain.output)
}

tasks.named<Jar>("jar") {
	from(commonMain.output)
}

tasks.named<ProcessResources>("processResources") {
	val props = mapOf("version" to project.version)
	inputs.properties(props)
	filesMatching("META-INF/mods.toml") { expand(props) }
}

java {
	sourceCompatibility = JavaVersion.toVersion(javaVersion)
	targetCompatibility = JavaVersion.toVersion(javaVersion)
}

tasks.withType<JavaCompile>().configureEach { options.release.set(javaVersion.toInt()) }

kotlin {
	compilerOptions { jvmTarget.set(JvmTarget.fromTarget(javaVersion)) }
}
