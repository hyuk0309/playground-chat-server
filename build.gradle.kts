import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
}

group = "elvis.chat"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")


    implementation("org.springframework.boot:spring-boot-devtools")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.code.gson:gson:2.8.0")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("io.jsonwebtoken:jjwt-gson:0.11.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // for front-end dependency
    implementation("org.webjars.bower:bootstrap:4.3.1")
    implementation("org.webjars.bower:vue:2.5.16")
    implementation("org.webjars.bower:axios:0.17.1")
    implementation("org.webjars:sockjs-client:1.1.2")
    implementation("org.webjars:stomp-websocket:2.3.3-1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
