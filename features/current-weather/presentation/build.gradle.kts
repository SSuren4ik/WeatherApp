plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.weatherapp.current_weather.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.designSystem)
    implementation(projects.features.currentWeather.domain)
    implementation(projects.features.currentWeather.data)
    implementation(libs.androidx.fragment.ktx)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
}