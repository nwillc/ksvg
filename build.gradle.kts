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
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask

val jvmTargetVersion = JavaVersion.VERSION_1_8.toString()

plugins {
    kotlin("multiplatform") version "1.3.61"
    `maven-publish`
    base
    id("org.jetbrains.dokka") version "0.10.0"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "com.github.nwillc"
version = "3.0.1-SNAPSHOT"

logger.lifecycle("${project.group}.${project.name}@${project.version}")

repositories {
    jcenter()
    mavenLocal()
}

kotlin {
    jvm {
        mavenPublication {
            artifactId = project.name
        }
    }
    js {
        useCommonJs()
        browser {
            runTask {
                sourceMaps = true
            }
            webpackTask {
                sourceMaps = true
            }
        }
        compilations.all {
            kotlinOptions {
                sourceMap = true
                metaInfo = true
                main = "call"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                setOf(
                    kotlin("stdlib-common")
                ).forEach { implementation(it) }
            }
        }
        val commonTest by getting {
            dependencies {
                setOf(
                    kotlin("test-common"),
                    kotlin("test-junit"),
                    kotlin("test-annotations-common")
                ).forEach { implementation(it) }
            }
        }
        val jvmMain by getting {
            dependencies {
                setOf(
                    kotlin("stdlib-jdk8")
                ).forEach { implementation(it) }
            }
        }
        val jvmTest by getting {
            dependencies {
                setOf(
                    kotlin("test-junit")
                ).forEach { implementation(it) }
            }
        }
        val jsMain by getting {
            dependencies {
                setOf(
                    kotlin("stdlib-js")
                ).forEach { implementation(it) }
            }
        }
        val jsTest by getting {
            dependencies {
                setOf(
                    kotlin("test-js")
                ).forEach { implementation(it) }
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    dryRun = false
    publish = true
    val pubs = publishing.publications
                   .map { it.name }
                   .filter { it != "kotlinMultiplatform" }
                   .toTypedArray()
    setPublications(*pubs)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
           repo = "maven"
           name = project.name
           desc = "Kotlin SVG generation DSL."
           websiteUrl = "https://github.com/nwillc/ksvg"
           issueTrackerUrl = "https://github.com/nwillc/ksvg/issues"
           vcsUrl = "https://github.com/nwillc/ksvg.git"
           version.vcsTag = "v${project.version}"
           setLicenses("ISC")
           setLabels("kotlin", "SVG", "DSL", "Multiplatform")
           publicDownloadNumbers = true
       })
}

tasks {
    withType<Test> {
        testLogging {
            showStandardStreams = true
            events("passed", "failed", "skipped")
        }
    }
    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "docs/dokka"
        configuration {
            includes = listOf("Module.md")
        }
    }
    withType<BintrayUploadTask> {
        onlyIf {
            if (project.version.toString().contains('-')) {
                logger.lifecycle("Version v${project.version} is not a release version - skipping upload.")
                false
            } else {
                true
            }
        }
    }
}
