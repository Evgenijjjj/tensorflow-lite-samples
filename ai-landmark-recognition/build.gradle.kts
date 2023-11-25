@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.evgeny.aiLandmarkRecognition"
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.permissions)

    // androidx
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // camerax
    implementation(libs.bundles.camerax.libs)

    // tf lite
    implementation(libs.bundles.tensorflow.lite)

    // archBase
    implementation(project(":arch-base"))
}
