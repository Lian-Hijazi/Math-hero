buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.6.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    alias(libs.plugins.kotlin.android) apply false
}

