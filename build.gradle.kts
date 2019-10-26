buildscript {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60-eap-25")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0-rc01")
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
