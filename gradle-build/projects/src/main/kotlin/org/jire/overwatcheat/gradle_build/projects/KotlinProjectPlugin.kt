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

package org.jire.overwatcheat.gradle_build.projects

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinProjectPlugin : Plugin<Project> {

    private fun configureCompileJava(project: Project) {
        project.run {
            plugins.withType<JavaPlugin>().configureEach {
                configure<JavaPluginExtension> {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            tasks.withType<JavaCompile>().configureEach {
                options.encoding = "UTF-8"
                options.release.set(17)
            }
        }
    }

    override fun apply(target: Project) {
        target.run {
            plugins.apply("org.jetbrains.kotlin.jvm")

            configureCompileJava(target)

            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }

            val kotlinPluginVersion = getKotlinPluginVersion()

            plugins.withType<KotlinPluginWrapper>().configureEach {
                dependencies {
                    configurations.named("api").configure {
                        for (module in kotlinModules) {
                            invoke("org.jetbrains.kotlin:kotlin-$module") {
                                version {
                                    strictly(kotlinPluginVersion)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private companion object {
        val kotlinModules = listOf("stdlib", "stdlib-common", "stdlib-jdk7", "stdlib-jdk8")
    }

}
