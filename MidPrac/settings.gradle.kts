pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MidPrac"
include(":app")
include(":ch6_view")
include(":ch7_layout")
include(":ch9_resource")
include(":ch8_event")
include(":ch10_dialog")
include(":ch11_jetpack")
include(":ch13_activity")
