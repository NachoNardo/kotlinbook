plugins {
    kotlin("jvm") version "2.2.10"
}

group = "com.holanda.leonardo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:3.3.0")
    implementation("io.ktor:ktor-server-netty:3.3.0")
}

tasks.test {
    useJUnitPlatform()
}