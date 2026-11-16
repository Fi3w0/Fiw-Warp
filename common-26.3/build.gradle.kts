import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("net.fabricmc.fabric-loom")
	kotlin("jvm")
}


val targetProperties = Properties().apply {
	val propertiesFile = file("gradle.properties")
	if (propertiesFile.exists()) propertiesFile.inputStream().use { load(it) }
}
fun target(name: String): String = targetProperties.getProperty(name) ?: providers.gradleProperty(name).get()
val minecraftVersion = target("minecraft_version")
val javaVersion = target("java_version")
val commonPath = if (minecraftVersion == "1.21.11") ":common" else ":common-$minecraftVersion"

base { archivesName.set("${providers.gradleProperty("archives_name").get()}-common-$minecraftVersion") }

dependencies {
	minecraft("com.mojang:minecraft:${minecraftVersion}")
}

java {
	sourceCompatibility = JavaVersion.toVersion(javaVersion)
	targetCompatibility = JavaVersion.toVersion(javaVersion)
}

tasks.withType<JavaCompile>().configureEach { options.release.set(javaVersion.toInt()) }

kotlin {
	compilerOptions { jvmTarget.set(JvmTarget.fromTarget(javaVersion)) }
}
