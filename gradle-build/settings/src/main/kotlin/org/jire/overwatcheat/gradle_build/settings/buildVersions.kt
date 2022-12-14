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
    version("gdx", "1.11.0")
}

fun VersionCatalogBuilder.buildMisc() {
    libraryVersioned("it.unimi.dsi", "fastutil", "8.5.9")
    libraryVersioned("org.bytedeco", "javacv-platform", "1.5.8")
    libraryVersioned("com.kotcrab.vis", "vis-ui", "1.5.1")
}

fun VersionCatalogBuilder.buildJNA() = buildGroup("net.java.dev.jna", "jna") {
    "jna"()
    "jna-platform"()
}

fun VersionCatalogBuilder.buildGDX() = buildGroup("com.badlogicgames.gdx", "gdx") {
    "gdx"()
    "gdx-platform"()

    "gdx-box2d"()
    "gdx-box2d-platform"()

    "gdx-freetype"()
    "gdx-freetype-platform"()

    "gdx-backend-lwjgl3"()
}

fun VersionCatalogBuilder.buildCatalog() {
    buildVersions()

    buildMisc()
    buildJNA()
    buildGDX()
}

@Suppress("UnstableApiUsage")
fun DependencyResolutionManagement.createVersionCatalogs(name: String = "libs") = versionCatalogs {
    create(name) {
        buildCatalog()
    }
}
