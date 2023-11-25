plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.evgeny.archBase"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
}

dependencies {
    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.libs)
    implementation(libs.androidx.lifecycle.common)
}
