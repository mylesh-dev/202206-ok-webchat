plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-webchat-transport-main-openapi"))
    implementation(project(":ok-webchat-common"))

    testImplementation(kotlin("test-junit"))
}
