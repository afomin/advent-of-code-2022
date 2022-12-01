import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "afomin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jcenter.bintray.com")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("khttp:khttp:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}