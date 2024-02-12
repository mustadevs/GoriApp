buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    //Dagger Hilt
    id("com.google.dagger.hilt.android") version "2.50" apply false

    //safeargs
    id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false

    //Google
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.relay") version "0.3.00"
}