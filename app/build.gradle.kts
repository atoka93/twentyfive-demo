plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.attilaszabo.twentyfivedemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.attilaszabo.twentyfivedemo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeCompiler {
        stabilityConfigurationFiles = listOf(
            rootProject.layout.projectDirectory.file("stability-config.conf")
        )
    }

}

dependencies {
    implementation(project(":common:di"))
    implementation(project(":common:presentation"))
    implementation(project(":features:quote:presentation"))
    implementation(project(":features:cutofftime:presentation"))

    testImplementation(project(":common:sharedtestresources"))
    androidTestImplementation(project(":common:sharedtestresources"))
}
