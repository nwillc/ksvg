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
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
//                implementation("org.assertj:assertj-core:$assertJVersion")
                implementation("com.github.javafaker:javafaker:$fakerVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
//                implementation("org.assertj:assertj-core:$assertJVersion")
            }
        }
    }
}

//dependencies {
//    listOf(
//        kotlin("stdlib-jdk8"),
//        "org.slf4j:slf4j-api:$slf4jApiVersion",
//        "$group:slf4jkext:$slf4jKextVersion"
//    )
//        .forEach { implementation(it) }
//
//    listOf(
//        "org.junit.jupiter:junit-jupiter-api:$jupiterVersion",
//        "org.assertj:assertj-core:$assertJVersion",
//        "io.mockk:mockk:$mockkVersion",
//        "uk.org.lidalia:slf4j-test:$slf4jTestVersion",
//        "com.github.javafaker:javafaker:$fakerVersion"
//    )
//        .forEach { testImplementation(it) }
//
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
//}
