val snapshotVersion : String? = System.getenv("COMPOSE_SNAPSHOT_ID")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        snapshotVersion?.let {
            maven {
                println("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") 
                maven { url = uri("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") }
            }
        }
    }
}

rootProject.name = "Labs20232-Gr07"
include(":Lab2-UI")
