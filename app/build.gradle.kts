plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.evgeny.tensorflowlitesamples"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.evgeny.tensorflowlitesamples"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extensions["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.extensions["javaVersion"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extensions["jvmTarget"] as String
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extensions["kotlinCompilerExtensionVersion"] as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.libs)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.permissions)

    // androidx
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // features
    implementation(project(":ai-landmark-recognition"))
}
