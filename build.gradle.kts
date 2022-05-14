plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    kotlin("android") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
