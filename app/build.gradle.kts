plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.google.firebase.appdistribution)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.developing.charityapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.developing.charityapplication"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "Beta Test 2.0"

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

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

// region -- Retrofit --
dependencies {
    //noinspection UseTomlInstead
    val lasted_Version = "2.11.0"
    implementation ("com.squareup.retrofit2:retrofit:$lasted_Version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.retrofit2:converter-gson:$lasted_Version")

    // coil
    implementation ("io.coil-kt:coil-compose:1.3.2")
}
// endregion

// region -- Hilt --
dependencies {
    //noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-android-compiler:2.55")
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
// endregion

// region -- Navigation --
dependencies {
    val nav_version = "2.8.6"

    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}
// endregion

// region -- Material --
dependencies {
    implementation ("androidx.compose.material3:material3:1.3.1")
}
// endregion

// region -- Kotlin Reflect --
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}
// endregion

// region -- Life Cycle --
dependencies{
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:31.2.3"))

    // Add the dependencies for the Firebase Cloud Messaging and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")
}
// endregion

// region -- Coil --

dependencies {
    implementation(libs.androidx.room.runtime.android)//noinspection UseTomlInstead
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
    implementation ("com.github.yalantis:ucrop:2.2.8")
}

// endregion

// region -- Animation --
dependencies {
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.34.0")
}
// endregion

// region -- Data Store Manager --

dependencies{
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
}

// endregion

// region -- Payment --
dependencies{
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.material:material:1.5.1")

    // Jsoup for web scraping
    // Jsoup để phân tích HTML
    implementation ("org.jsoup:jsoup:1.16.1")
    // ZXing để xử lý mã QR
    implementation ("com.google.zxing:core:3.5.3")
    implementation ("com.google.zxing:javase:3.5.3")

    // Coroutines for async operations
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
// endregion

