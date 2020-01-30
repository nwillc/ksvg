/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

val coverageThreshold = 0.98
val jvmTargetVersion = JavaVersion.VERSION_1_8.toString()
val publicationName = "maven"

val assertJVersion: String by project
val fakerVersion: String by project
val gradleVersion: String by project
val jacocoToolVersion: String by project
val jupiterVersion: String by project
val ktlintToolVersion: String by project
val mockkVersion: String by project

plugins {
    kotlin("multiplatform") version "1.3.61"
}

group = "com.github.nwillc"
version = "3.0.0-SNAPSHOT"

logger.lifecycle("${project.group}.${project.name}@${project.version}")

repositories {
    jcenter()
}

kotlin {
    jvm()
    js()
    sourceSets {
        val commonMain by getting {
            dependencies {
                listOf(
                    kotlin("stdlib-common")
                ).forEach { implementation(it) }
            }
        }
        val commonTest by getting {
            dependencies {
                listOf(
                    kotlin("test-common"),
                    kotlin("test-junit"),
                    kotlin("test-annotations-common")
                ).forEach { implementation(it) }
            }
        }
        val jvmMain by getting {
            dependencies {
                listOf(
                    kotlin("stdlib-jdk8")
                ).forEach { implementation(it) }
            }
        }
        val jvmTest by getting {
            dependencies {
                listOf(
                    kotlin("test-junit")
                ).forEach { implementation(it) }
            }
        }
        val jsMain by getting {
            dependencies {
                listOf(
                    kotlin("stdlib-js")
                ).forEach { implementation(it) }
            }
        }
    }
}
