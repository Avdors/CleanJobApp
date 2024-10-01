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

rootProject.name = "CleanJobApp"
include(":app")
include(":shared")
include(":shared:core")
include(":feature")
include(":feature:cardvacancy")
include(":feature:listVacancy")
include(":feature:favoritevacancy")
include(":feature:responsvacancy")

