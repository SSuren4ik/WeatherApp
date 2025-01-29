import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.login"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "LOGIN", getDebugLoginData().first)
            buildConfigField("String", "PASSWORD", getDebugLoginData().second)
        }
        release {
            buildConfigField("String", "LOGIN", getReleaseLoginData().first)
            buildConfigField("String", "PASSWORD", getReleaseLoginData().second)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.material)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun getDebugLoginData(): Pair<String, String> {
    val properties = Properties()
    file("../local.properties").inputStream().use { properties.load(it) }
    val login = properties.getProperty("DEBUG_LOGIN", "")
    val password = properties.getProperty("DEBUG_PASSWORD", "")
    return Pair(login, password)
}

fun getReleaseLoginData(): Pair<String, String> {
    val properties = Properties()
    file("../local.properties").inputStream().use { properties.load(it) }
    val login = properties.getProperty("RELEASE_LOGIN", "")
    val password = properties.getProperty("RELEASE_PASSWORD", "")
    return Pair(login, password)
}