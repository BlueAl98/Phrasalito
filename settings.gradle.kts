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
include(":core:utils")
include(":core:datastore")
include(":phrasalito:feature_startScreen")
include(":phrasalito:feature_deckScreen")
include(":core:tts")
include(":core:translation")
include(":core:database")
include(":phrasalito:feature_categories")
include(":phrasalito:feature_phrases")
include(":phrasalito:feature_languages")
