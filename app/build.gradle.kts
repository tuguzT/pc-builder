import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.AndroidBase.androidBaseImplementation
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.DomainLayer
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.Hilt.hiltImplementation
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.JetpackCompose
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.KotlinX
import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32
    namespace = "io.github.tuguzt.pcbuilder"

    defaultConfig {
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = JetpackCompose.version
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    // Domain layer
    implementation(DomainLayer.dependency) { isChanging = true }

    // Required Android dependencies
    androidBaseImplementation()

    // Kotlin Extensions
    implementation(KotlinX.Coroutines.dependency)
    implementation(KotlinX.serializationJson)

    // Jetpack Compose
    composeCoreImplementation()
    materialDesignImplementation()
    composeNavigationImplementation()

    // Additional features for Jetpack Compose
    composeThirdPartyImplementation()
    accompanistImplementation()

    // Persistence
    roomImplementation()

    // Dependency injection
    hiltImplementation()

    // Retrofit
    retrofitImplementation()

    // Quality Assurance
    androidTestImplementation(KotlinX.Test.coroutines) {
        exclude(group = KotlinX.group, module = KotlinX.Test.excludedModule)
    }
    loggingImplementation()
    unitTestingImplementation()
    instrumentTestingImplementation()
}

kapt {
    correctErrorTypes = true
}
