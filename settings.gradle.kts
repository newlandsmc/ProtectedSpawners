rootProject.name = "ProtectedSpawners"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { // Paper
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }
        maven { // Configurate
            url = uri("https://repo.spongepowered.org/maven")
        }
        maven { // run paper plugin
            url = uri("https://repo.jpenilla.xyz/snapshots/")
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}