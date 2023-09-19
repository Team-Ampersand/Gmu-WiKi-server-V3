
plugins {
    kotlin("jvm") version "1.8.22"
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        version = "1.8.22"
    }

    apply {
        plugin("org.jetbrains.kotlin.kapt")
        version = "1.6.10"
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.3")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    }
}

allprojects {
    group = "team.ampersand"
    version = "0.0.1-SNAPSHOT"

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        compileJava {
            sourceCompatibility = JavaVersion.VERSION_17.majorVersion
        }

        test {
            useJUnitPlatform()
        }
    }

    repositories {
        mavenCentral()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}