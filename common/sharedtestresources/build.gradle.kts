plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.attilaszabo.twentyfivedemo.common.sharedtestresources"
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
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(platform(libs.androidx.compose.bom))
    debugApi(libs.androidx.ui.test.manifest)
    api(libs.androidx.ui.test.junit4)

    testApi(libs.junit)
    testApi(libs.coroutines.test)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)
    androidTestApi(platform(libs.androidx.compose.bom))
    debugApi(libs.androidx.ui.test.manifest)
    androidTestApi(libs.androidx.ui.test.junit4)
}