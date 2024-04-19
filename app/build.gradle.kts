plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //Google
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.relay") version "0.3.00"

    //Dagger Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    //Safeargs
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.mustadevs.goriapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mustadevs.goriapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Nav component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")



    //DaggerHilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Google Ads
    implementation("com.google.android.gms:play-services-ads:22.6.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")


    //clases selladas sealed class
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Firebase  Gradle
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")

    //Notification Badge
    implementation("com.nex3z:notification-badge:1.0.4")
    //Eventbus
    implementation("org.greenrobot:eventbus:3.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1") // Asegúrate de utilizar la versión más reciente


    //login
    // Fragment
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    // Activity
    implementation ("androidx.activity:activity-ktx:1.4.0")
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc01")
    //Corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.1")
    //Firebase


    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    //Timber
    implementation ("com.jakewharton.timber:timber:4.7.1")
    //Lottie
    implementation ("com.airbnb.android:lottie:5.0.3")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}

