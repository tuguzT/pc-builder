plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    kotlin("android") version "1.6.21" apply false
    kotlin("plugin.serialization") version "1.6.21" apply false
}

buildscript {
    dependencies {
        classpath(io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.Hilt.plugin)
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
