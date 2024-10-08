plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.graphics")
}

val ktorVersion = "2.3.11"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion") // Если используете OkHttp
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("com.github.kwhat:jnativehook:2.2.2")

    implementation("no.tornado:tornadofx:1.7.20")
}

tasks {
    shadowJar {
        archiveBaseName.set("RustKeyGen")
        archiveVersion.set("1.0")
        archiveClassifier.set("")
    }
}

application {
    mainClass.set("ru.freestyle.keygen.MainKt")
}