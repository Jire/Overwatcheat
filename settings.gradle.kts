rootProject.name = "Overwatcheat"

pluginManagement {
    @Suppress("UnstableApiUsage")
    includeBuild("gradle-build")
}

plugins {
    id("overwatcheat-settings")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
