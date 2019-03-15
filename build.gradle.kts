import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val coverageThreshold = 0.98
val jvmTargetVersion = JavaVersion.VERSION_1_8.toString()
val publicationName = "maven"

val assertJVersion: String by project
val fakerVersion: String by project
val jacocoToolVersion: String by project
val jupiterVersion: String by project
val mockkVersion: String by project
val slf4jApiVersion: String by project
val slf4jTestVersion: String by project

plugins {
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.3.21"
    id("com.github.nwillc.vplugin") version "2.3.0"
    id("org.jetbrains.dokka") version "0.9.17"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jlleitschuh.gradle.ktlint") version "7.1.0"
}

group = "com.github.nwillc"
version = "2.2.4-SNAPSHOT"

logger.lifecycle("${project.group}.${project.name}@${project.version}")

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:$slf4jApiVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("uk.org.lidalia:slf4j-test:$slf4jTestVersion")
    testImplementation("com.github.javafaker:javafaker:$fakerVersion")

    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

detekt {
    input = files("src/main/kotlin")
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokka")
    classifier = "javadoc"
    from("$buildDir/javadoc")
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
    reportsDir = file("$buildDir/customJacocoReportDir")
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
        includeNonPublic = false
        outputDirectory = "$buildDir/dokka"
        includes = arrayListOf("Module.md")
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
