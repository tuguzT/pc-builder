plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    kotlin("android") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10" apply false
}

buildscript {
    extra.let {
        it["composeVersion"] = "1.1.1"
        it["koinVersion"] = "3.2.0-beta-1"
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
