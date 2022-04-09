plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    kotlin("android") version "1.6.20" apply false
    kotlin("plugin.serialization") version "1.6.20" apply false
}

buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
