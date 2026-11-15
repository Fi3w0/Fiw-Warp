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
include("common-1.21.8")
include("fabric-1.21.8")
include("neoforge-1.21.8")
