plugins {
    `kotlin-dsl`
}

group = "org.jire.overwatcheat.gradle_build"
version = "0.1.0"

gradlePlugin.plugins.register("overwatcheat-versions") {
    id = name
    implementationClass = "com.near_reality.gradle.versions.VersionsPlugin"
}
