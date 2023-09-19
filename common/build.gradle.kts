import org.jetbrains.kotlin.builtins.StandardNames.FqNames.annotation

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
}

dependencies {
    implementation("org.hibernate.reactive:hibernate-reactive-core:1.1.3.Final")
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:2.0.1.RELEASE")
    implementation("org.springframework.data:spring-data-commons")
    implementation("io.vertx:vertx-mysql-client:4.2.5")
    implementation("io.smallrye.reactive:mutiny-kotlin:1.4.0")
    implementation("io.smallrye.reactive:mutiny-reactor:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.1")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    testImplementation("com.wix:wix-embedded-mysql:4.6.2")
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    implementation(project(":api"))
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.unmappedTargetPolicy", "ignore")
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}