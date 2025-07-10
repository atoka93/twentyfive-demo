plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.attilaszabo.twentyfivedemo.di"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":features:cutofftime:presentation"))
    implementation(project(":features:cutofftime:domain"))
    implementation(project(":features:cutofftime:data"))
    implementation(project(":features:quote:presentation"))
    implementation(project(":features:quote:domain"))
    implementation(project(":features:quote:data"))
    implementation(project(":sources:country"))
    implementation(project(":sources:cutofftime"))
    implementation(project(":sources:quote-api"))
    implementation(project(":sources:quote-cache"))

    implementation(libs.androidx.navigation)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.navigation)

    implementation(libs.datastore.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
