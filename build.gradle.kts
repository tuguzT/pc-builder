plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    kotlin("android") version "1.6.10" apply false
}

buildscript {
    extra.apply {
        this["composeVersion"] = "1.1.1"
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
