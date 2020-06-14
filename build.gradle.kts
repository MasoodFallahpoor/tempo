buildscript {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-alpha10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0-rc01")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
