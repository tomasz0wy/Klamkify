plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "kopycinski.tomasz.klamkify"
    compileSdk = 33

    defaultConfig {
        applicationId = "kopycinski.tomasz.klamkify"
        minSdk = 26
        targetSdk = 33
        versionCode = 5
        versionName = "0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.5.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Compose Navigation
    val nav_version = "2.5.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Hilt
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Room
    val room_version = "2.5.1"
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // Compose
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.ui:ui:${rootProject.ext["compose_ui_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.ext["compose_ui_version"]}")
    implementation("androidx.compose.material:material:${rootProject.ext["compose_ui_version"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.ext["compose_ui_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.ext["compose_ui_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.ext["compose_ui_version"]}")

    // Core
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":domain"))
}

kapt {
    correctErrorTypes = true
}