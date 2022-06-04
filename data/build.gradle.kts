import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.AndroidBase
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.DomainLayer
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.KotlinX
import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.retrofitImplementation
import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.roomImplementation
import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.unitTestingImplementation

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    implementation(KotlinX.serializationJson)
    implementation(AndroidBase.security)
    implementation(AndroidBase.core)
    roomImplementation()
    retrofitImplementation()
    implementation(DomainLayer.dependency) { isChanging = true }
    unitTestingImplementation()
}

kapt {
    correctErrorTypes = true
}
