plugins {
    `kotlin-dsl`
}

group = "org.jire.overwatcheat.gradle_build"
version = "0.1.0"

val kotlinVersion = "1.8.0-RC"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

gradlePlugin {
    plugins {
        register("overwatcheat-kotlin-project") {
            id = name
            implementationClass = "org.jire.overwatcheat.gradle_build.projects.KotlinProjectPlugin"
        }
    }
}
