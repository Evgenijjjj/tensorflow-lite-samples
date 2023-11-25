// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

project.extensions.apply {
    add("javaVersion", JavaVersion.VERSION_17)
    add("jvmTarget", "17")
    add("kotlinCompilerExtensionVersion", "1.4.4")
}
