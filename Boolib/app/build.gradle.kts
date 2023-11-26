@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    //kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
}

android {
    namespace = "by.bsuir.khimich.boolib"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.bsuir.khimich.boolib"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schema")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    implementation(libs.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    // optional - RxJava2 support for Room
    implementation(libs.androidx.room.rxjava2)

    // optional - RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.androidx.room.guava)

    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui.test.junit4)
    implementation(libs.ui.tooling)
    implementation(libs.ui.test.manifest)

    implementation(libs.androidx.navigation)
    implementation(libs.androidx.material)


    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.accompanist.adaptive)
    implementation(libs.accompanist.testharness)
    implementation(libs.collection.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)

    implementation(libs.constraintlayout.compose)
    implementation(libs.foundation)
    implementation(libs.paging.compose)
    implementation(libs.runtime)
    implementation(libs.ui.util)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //implementation(libs.insert.koin.koin.androidx.viewmodel)

    implementation(libs.core)
    implementation(libs.github.core)
    implementation(libs.compose.destinations.ksp)
    implementation(libs.github.core)
    implementation(libs.animations.core.v193)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    //implementation(libs.koin.androidx.viewmodel)

    implementation(libs.work.runtime)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.client.encoding)

    implementation(libs.lifecycle.process)
    implementation(libs.lifecycle.service)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.paging.runtime)
    implementation(libs.paging.common)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui.test.junit4)
    implementation(libs.ui.tooling)
    implementation(libs.ui.test.manifest)
}
