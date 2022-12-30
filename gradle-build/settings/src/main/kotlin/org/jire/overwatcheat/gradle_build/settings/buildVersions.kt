/*
 * Free, open-source undetected color cheat for Overwatch!
 * Copyright (C) 2017  Thomas G. Nappo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jire.overwatcheat.gradle_build.settings

import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import org.gradle.api.initialization.resolve.DependencyResolutionManagement
import org.jire.overwatcheat.gradle_build.versions.Versions

fun VersionCatalogBuilder.buildVersions() {
    version("kotlin", Versions.KOTLIN)

    version("jna", "5.12.1")
    version("slf4j", "2.0.6")
}

fun VersionCatalogBuilder.buildMisc() {
    libraryVersioned("it.unimi.dsi", "fastutil", "8.5.11")
    libraryVersioned("org.bytedeco", "javacv-platform", "1.5.8")
}

fun VersionCatalogBuilder.buildJNA() = buildGroup("net.java.dev.jna", "jna") {
    "jna"()
    "jna-platform"()
}

fun VersionCatalogBuilder.buildOpenHFT() {
    libraryVersioned("net.openhft", "affinity", "3.23.2")
    libraryVersioned("net.openhft", "chronicle-core", "2.24ea3")
}

fun VersionCatalogBuilder.buildSLF4J() = buildGroup("org.slf4j", "slf4j") {
    "slf4j-api"()
    "slf4j-simple"()
}

fun VersionCatalogBuilder.buildCatalog() {
    buildVersions()

    buildMisc()
    buildJNA()
    buildOpenHFT()
    buildSLF4J()
}

@Suppress("UnstableApiUsage")
fun DependencyResolutionManagement.createVersionCatalogs(name: String = "libs") = versionCatalogs {
    create(name) {
        buildCatalog()
    }
}
