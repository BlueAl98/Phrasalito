pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven ( url = "https://jitpack.io" )

        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ( url = "https://jitpack.io" )

    }
}

rootProject.name = "Phrasalito"
include(":app")
include(":phrasalito:phrasalito_data")
include(":phrasalito:phrasalito_domain")
include(":phrasalito:phrasalito_presentation")
include(":phrasalito:common")
