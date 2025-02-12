enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

rootProject.name = "TheWeatherApp"
include(":app")
include(":core")
include(":design-system")
include(":features")

include(":features:login")
include(":features:splash")
include(":features:current-weather")
include(":features:current-weather:data")
include(":features:current-weather:domain")
include(":features:current-weather:presentation")
include(":features:world-weather")