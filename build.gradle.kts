import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask

val assertJVersion = "3.11.1"
val jacocoToolVersion = "0.8.2"
val jupiterVersion = "5.3.2"
val jvmTargetVersion = "1.8"
val mockkVersion = "1.8.13.kotlin13"

plugins {
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.3.10"
    id("com.github.nwillc.vplugin") version "2.2.2"
    id("org.jetbrains.dokka") version "0.9.17"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.github.ngyewch.git-version") version "0.2"
    id("org.jmailen.kotlinter") version "1.20.1"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "com.github.nwillc"
version = gitVersion.gitVersionInfo.gitVersionName.substring(1)

logger.lifecycle("${project.name} $version")

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

detekt {
    input = files("src/main/kotlin")
    filters = ".*/build/.*"
}

jacoco {
    toolVersion = jacocoToolVersion
}

gitVersion {
    gitTagPrefix = "v"
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

val publicationName = "maven"
publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
            artifact(tasks["sourcesJar"])
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
        repo = "maven"
        name = project.name
        desc = "Kotlin SVG generation DSL."
        websiteUrl = "https://github.com/nwillc/ksvg"
        issueTrackerUrl = "https://github.com/nwillc/ksvg/issues"
        vcsUrl = "https://github.com/nwillc/ksvg.git"
        version.vcsTag = gitVersion.gitVersionInfo.gitVersionName
        setLicenses("ISC")
        setLabels("kotlin", "SVG", "DSL")
        publicDownloadNumbers = true
    })
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTargetVersion
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
    }
    withType<DokkaTask> {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"
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
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${project.name}-$version.pom")
    }
    withType<BintrayUploadTask> {
        onlyIf {
            if (gitVersion.gitVersionInfo.gitVersionName.contains('-')) {
                logger.lifecycle("Version ${gitVersion.gitVersionInfo.gitVersionName} is not a release version - skipping upload.")
                false
            } else {
                true
            }
        }
    }
}

