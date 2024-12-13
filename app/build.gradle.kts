plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//
//    id("kotlin-kapt")
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.shoppermova"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoppermova"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

//    // LOCATION
//    implementation("com.google.android.gms:play-services-location:21.0.1")
//
//    // TINDER LIKE CARD
//    implementation("com.alexstyl.swipeablecard:swipeablecard:0.1.0")
//
//    // ASYNC IMAGE
//    implementation("io.coil-kt:coil-compose:2.5.0")
//
    // MAPS
    implementation("com.google.maps.android:maps-compose:6.4.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

//    // HILT
//    implementation("com.google.dagger:hilt-android:2.53")
//    kapt("com.google.dagger:hilt-android-compiler:2.53")

    // KOIN
//    implementation("io.insert-koin:koin-android:3.2.0-beta-1")
//    implementation("io.insert-koin:koin-androidx-navigation:3.2.0-beta-1")
//    implementation("io.insert-koin:koin-androidx-compose:3.2.0-beta-1")
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    api(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

//
    // VIEW MODEL
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
//
    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // PAGING
    implementation("androidx.paging:paging-runtime:3.3.4")
    implementation("androidx.paging:paging-compose:3.3.4")

    implementation("androidx.compose.material:material-icons-extended:1.7.5")


//
//    // LOAD IMAGES
//    implementation("io.coil-kt:coil-compose:2.5.0")




//    // MAPS
//    implementation(libs.maps.compose)
//    implementation(libs.play.services.maps)
//
//    // HILT
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.android.compiler)
//
//    // VIEW MODEL
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
//
//    // COROUTINES
//    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.kotlinx.coroutines.android)
//
//    // RETROFIT
//    implementation(libs.retrofit)
//    implementation(libs.converter.gson)
//
//    // NAVIGATION
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}