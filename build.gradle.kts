
plugins {
    kotlin("jvm")           apply false
    kotlin("multiplatform") apply false
}

group   = "net.murzikov.webchat"
version = "0.0.1"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group   = rootProject.group
    version = rootProject.version
}
