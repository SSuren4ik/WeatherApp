import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.weatherapp.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "API_KEY", getApiKey())
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}

fun getApiKey(): String {
    val properties = Properties()
    file("../local.properties").inputStream().use { properties.load(it) }
    val key = properties.getProperty("API_KEY", "")
    return key
}
