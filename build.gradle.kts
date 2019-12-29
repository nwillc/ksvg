import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
val slf4jApiVersion: String by project
val slf4jKextVersion: String by project
val slf4jTestVersion: String by project

plugins {
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.3.61"
    id("com.github.nwillc.vplugin") version "3.0.1"
    id("org.jetbrains.dokka") version "0.10.0"
    id("io.gitlab.arturbosch.detekt") version "1.3.0"
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

group = "com.github.nwillc"
version = "2.3.4"

logger.lifecycle("${project.group}.${project.name}@${project.version}")

repositories {
    jcenter()
}

dependencies {
    listOf(
        kotlin("stdlib-jdk8"),
        "org.slf4j:slf4j-api:$slf4jApiVersion",
        "$group:slf4jkext:$slf4jKextVersion"
    )
        .forEach { implementation(it) }

    listOf(
        "org.junit.jupiter:junit-jupiter-api:$jupiterVersion",
        "org.assertj:assertj-core:$assertJVersion",
        "io.mockk:mockk:$mockkVersion",
        "uk.org.lidalia:slf4j-test:$slf4jTestVersion",
        "com.github.javafaker:javafaker:$fakerVersion"
    )
        .forEach { testImplementation(it) }

    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

ktlint {
    version.set(ktlintToolVersion)
}

detekt {
    toolVersion = "1.0.0-RC14"
    input = files("src/main/kotlin")
    reports {
        xml {
            enabled = true
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.html")
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokka")
    classifier = "javadoc"
    from("$buildDir/dokka")
}

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    dryRun = false
    publish = true
    setPublications(publicationName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = publicationName
        name = project.name
        desc = "Kotlin SVG generation DSL."
        websiteUrl = "https://github.com/nwillc/ksvg"
        issueTrackerUrl = "https://github.com/nwillc/ksvg/issues"
        vcsUrl = "https://github.com/nwillc/ksvg.git"
        version.vcsTag = "v${project.version}"
        setLicenses("ISC")
        setLabels("kotlin", "SVG", "DSL")
        publicDownloadNumbers = true
    })
}

jacoco {
    toolVersion = jacocoToolVersion
}

tasks {
    named("check") {
        dependsOn(":jacocoTestCoverageVerification")
    }
    named<Jar>("jar") {
        manifest.attributes["Automatic-Module-Name"] = "${project.group}.${project.name}"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTargetVersion
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
        beforeTest(KotlinClosure1<TestDescriptor, Unit>({ logger.lifecycle("  Should ${this.name}") }))
        afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ descriptor, result ->
            if (descriptor.parent == null) {
                logger.lifecycle("\nTests run: ${result.testCount}, Failures: ${result.failedTestCount}, Skipped: ${result.skippedTestCount}")
            }
            Unit
        }))
    }
    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        configuration {
            includes = listOf("Module.md")
        }
    }
    withType<JacocoReport> {
        dependsOn("test")
        reports {
            xml.apply {
                isEnabled = true
            }
            html.apply {
                isEnabled = true
            }
        }
    }
    jacocoTestCoverageVerification {
        dependsOn("jacocoTestReport")
        violationRules {
            rule {
                limit {
                    minimum = coverageThreshold.toBigDecimal()
                }
            }
        }
    }
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${project.name}-${project.version}.pom")
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
