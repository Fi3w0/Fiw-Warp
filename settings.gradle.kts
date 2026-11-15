pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		gradlePluginPortal()
		mavenCentral()
	}
}

rootProject.name = "fiw-warp"

include("common")
include("fabric")
include("neoforge")

include("common-26.1.2")
include("fabric-26.1.2")
