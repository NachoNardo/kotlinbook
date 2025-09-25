plugins {
    kotlin("jvm") version "2.2.10"
    application
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
    implementation("ch.qos.logback:logback-classic:1.4.11")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.13")
    implementation("io.ktor:ktor-server-status-pages:3.3.0")
    implementation("com.typesafe:config:1.4.5")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("kotlinbook.MainKt")
}
