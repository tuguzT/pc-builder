// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
        classpath(kotlin("serialization", version = "1.5.31"))
    }
}
