import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.publish.maven.MavenPom
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask

plugins {
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.3.10"
    id("com.github.nwillc.vplugin") version "2.1.1"
    id("org.jetbrains.dokka") version "0.9.17"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.github.ngyewch.git-version") version "0.2"
    id("org.jmailen.kotlinter") version "1.20.1"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "com.github.nwillc"
version = "${gitVersion.gitVersionInfo.gitVersionName.substring(1)}"

logger.lifecycle("${project.name} ${version}")

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testCompile("org.assertj:assertj-core:3.11.1")

    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.3.1")

    testImplementation("io.mockk:mockk:1.8.13.kotlin13")
}

detekt {
    input = files("src/main/kotlin")
    filters = ".*/build/.*"
}

jacoco {
    toolVersion = "0.8.2"
}

gitVersion {
    gitTagPrefix = "v"
}

val publicationName = "maven"
publishing {
    publications {
        register(publicationName, MavenPublication::class) {
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
        repo = "maven"
        name = "${project.name}"
        desc = "Kotlin SVG generation DSL."
        websiteUrl = "https://github.com/nwillc/ksvg"
        issueTrackerUrl = "https://github.com/nwillc/ksvg/issues"
        vcsUrl = "https://github.com/nwillc/ksvg.git"
        version.vcsTag = "${gitVersion.gitVersionInfo.gitVersionName}"
        setLicenses("ISC")
        setLabels("kotlin","SVG","DSL")
        publicDownloadNumbers = true
    })
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
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
        destination = file("${buildDir}/libs/${project.name}-${version}.pom")
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

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokka")
    classifier = "javadoc"
    from("$buildDir/javadoc")
}





