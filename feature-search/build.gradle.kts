import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.feature_search"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField(
            "String",
            "OPENROUTER_API_KEY",
            "\"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\""
        )
        buildConfigField(
            "String",
            "ADMIN_OPENROUTER_API_KEY_2",
            "\"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\""
        )
        buildConfigField(
            "String",
            "ADMIN_OPENROUTER_API_KEY_V2",
            "\"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\""
        )
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        android.buildFeatures.buildConfig = true
    }
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    implementation(project(":feature-response"))

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.kotlinx.serialization.json.v173)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation (libs.lottie)
    implementation(libs.hilt.android.v2562)
    ksp(libs.hilt.android.compiler)


}