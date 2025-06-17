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
include(":di")
include(":quote-presentation")
include(":quote-domain")
include(":quote-data")
include(":quote-api")
include(":quote-cache")
include(":common-presentation")
include(":common-domain")
