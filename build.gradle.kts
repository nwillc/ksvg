import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco
    kotlin("jvm") version "1.3.10"
    // `maven-publish`
//    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.nwillc.vplugin") version "2.1.1"
    id("org.jetbrains.dokka") version "0.9.17"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.github.ngyewch.git-version") version "0.2"
    id("org.jmailen.kotlinter") version "1.20.1"
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

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }

    withType<DokkaTask> {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"
    }

    withType<JacocoReport> {
        reports {
            xml.apply {
                isEnabled = true
            }
            html.apply {
                isEnabled = true
            }
        }
    }
}


//val sourcesJar by tasks.registering(Jar::class) {
//    classifier = "sources"
//    from(sourceSets["main"].allSource)
//}

//
//task javadocJar(type: Jar, dependsOn: ['dokka']) {
//    from "$buildDir/javadoc"
//    classifier = 'javadoc'
//}
//

//publishing {
//    publications {
//        maven(MavenPublication) {
//            from components.java
//            artifact sourceJar
//            artifact javadocJar
//            pom.withXml {
//                asNode().dependencies.'*'.findAll() {
//                    it.scope.text() == 'runtime' && project.configurations.compile.allDependencies.find { dep ->
//                        dep.name == it.artifactId.text()
//                    }
//                }.each() {
//                    it.scope*.value = 'compile'
//                }
//            }
//        }
//    }
//}

//
//publish.dependsOn = ['assemble', 'sourceJar', 'javadocJar', 'generatePomFileForMavenPublication']
//
//model {
//    tasks.generatePomFileForMavenPublication {
//        destination = file("${buildDir}/libs/${project.name}-${version}.pom")
//    }
//}
//
//bintrayUpload.onlyIf { !project.version.toString().contains('-') }
//
//bintray {
//    user = System.getenv("BINTRAY_USER")
//    key = System.getenv("BINTRAY_API_KEY")
//
//    dryRun = false
//    publish = true
//  //  publications = ["maven"]
//    pkg {
//        repo = "maven"
//        name = project.name
//        desc = "Kotlin SVG generation DSL."
//        websiteUrl = "https://github.com/nwillc/ksvg"
//        issueTrackerUrl = "https://github.com/nwillc/ksvg/issues"
//        vcsUrl = "https://github.com/nwillc/ksvg.git"
////        licenses = ["ISC"]
////        labels = ['kotlin', 'SVG', 'DSL']
//        publicDownloadNumbers = true
//    }
//}
//

//
//task ghPages(dependsOn: dokka) {
//    copy {
//        from('build/javadoc')
//        into('docs/javadoc/')
//    }
//}


detekt {
    input = files("src/main/kotlin")
    filters = ".*/build/.*"
}

jacoco {
    toolVersion = "0.8.2"
}

//jacocoTestReport.dependsOn test
//

gitVersion {
    gitTagPrefix = "v"                  // Tag prefix to extract version from.
}
