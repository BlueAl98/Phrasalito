plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.nayibit.phrasalito_presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(project(":phrasalito:phrasalito_domain"))
    implementation(project(":phrasalito:common"))
    //HILT VIEWMODEL JETCOMPOSE
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.foundation)

    implementation( libs.material.icons.extended)


    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.material3.window.size.class1.android)

    //Dagger hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    //WorkManager
    implementation (libs.androidx.work.runtime.ktx)

    // Hilt with WorkManager
    implementation (libs.androidx.hilt.work)
    ksp (libs.androidx.hilt.compiler)


   // implementation ("com.github.smartword-app:compose-swipeable-cards:1.1.4")
    implementation (libs.compose.swipeable.cards)


    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
   // implementation(libs.androidx.constraintlayout)
   // implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    // Unit testing
    testImplementation (libs.junit)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.turbine) // for testing Flow
// Optional for lifecycle & ViewModel
    testImplementation (libs.androidx.core.testing)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}