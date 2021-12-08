import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val lwjglVersion = "3.3.0"
val lwjglNatives = "natives-windows"

plugins {
    kotlin("jvm") version "1.6.0"
}

group = "me.76img"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test", "1.6.0"))

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl", version = lwjglVersion)
    implementation("org.lwjgl", "lwjgl-assimp", version = lwjglVersion)
    implementation("org.lwjgl", "lwjgl-glfw", version = lwjglVersion)
    implementation("org.lwjgl", "lwjgl-openal", version = lwjglVersion)
    implementation("org.lwjgl", "lwjgl-opengl", version = lwjglVersion)
    implementation("org.lwjgl", "lwjgl-stb", version = lwjglVersion)
    runtimeOnly("org.lwjgl", "lwjgl", version = lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", version = lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", version = lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", version = lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", version = lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", version = lwjglVersion, classifier = lwjglNatives)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}