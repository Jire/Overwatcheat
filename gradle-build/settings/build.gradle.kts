plugins {
    `kotlin-dsl`
}

group = "org.jire.overwatcheat.gradle_build"
version = "0.1.0"

dependencies {
    implementation(project(":versions"))
}

gradlePlugin.plugins.register("overwatcheat-settings") {
    id = name
    implementationClass = "org.jire.overwatcheat.gradle_build.settings.SettingsPlugin"
}
