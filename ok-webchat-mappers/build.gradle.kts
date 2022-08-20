plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val datetimeVersion: String by project
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(project(":ok-webchat-transport-main-openapi"))
    implementation(project(":ok-webchat-common"))

    testImplementation(kotlin("test-junit"))
}
