buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.kotlin.serialization)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
    kotlin("jvm") version "1.9.23"
}
true // Needed to make the Suppress annotation work for the plugins block