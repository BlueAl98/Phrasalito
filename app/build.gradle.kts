import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.nayibit.phrasalito"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nayibit.phrasalito"
        minSdk = 29
        targetSdk = 35
        versionCode = 2
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //URL_API
        buildConfigField("String", "BASE_URL", "\"http://Base_URL/v1/api/\"")


    }

    signingConfigs {

        val keystorePath = System.getenv("ANDROID_KEYSTORE_PATH")

        if (keystorePath != null) {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }


    buildTypes {
        release {
            signingConfigs.findByName("release")?.let {
                signingConfig = it
            }
            isMinifyEnabled = true
            isShrinkResources = true
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
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}





dependencies {

    implementation(project(":phrasalito:phrasalito_presentation"))
    implementation(project(":phrasalito:phrasalito_data"))


    //Navegacion screens
    //Navigation libs
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // retrofit
    implementation (libs.retrofit)
    // gson converter
    implementation (libs.converter.gson)

    // OKHTTP and interceptor
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.appcompat)

    //Dagger hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    //ROOM DataBase
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Data Store
    implementation (libs.androidx.datastore.preferences) // Asegúrate de usar la versión adecuada


    //HILT VIEWMODEL JETCOMPOSE
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)

    //ICONS
    //Icons
    implementation (libs.androidx.material.icons.extended)


    //WorkManager
    implementation (libs.androidx.work.runtime.ktx)

    // Hilt with WorkManager
    implementation (libs.androidx.hilt.work)
    ksp (libs.androidx.hilt.compiler)

    implementation (libs.compose.swipeable.cards)


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