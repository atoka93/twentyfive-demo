plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(libs.coroutines.core)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}
