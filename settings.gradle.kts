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

rootProject.name = "twentyfivedemo"
include(":app")
include(":sources:quote-api")
include(":sources:quote-cache")
include(":sources:cutofftime")
include(":sources:country")

include(":features:quote:presentation")
include(":features:quote:domain")
include(":features:quote:data")

include(":features:cutofftime:data")
include(":features:cutofftime:domain")
include(":features:cutofftime:presentation")

include(":common:domain")
include(":common:presentation")
include(":common:di")
include(":common:sharedtestresources")
