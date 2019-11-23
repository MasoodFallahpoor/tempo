plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        applicationId = "ir.fallahpoor.tempo"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )

        }
    }
}

dependencies {
    implementation(Dependencies.App.kotlin)
    implementation(Dependencies.App.appCompat)
    implementation(Dependencies.App.coreKtx)
    implementation(Dependencies.App.constraintLayout)
    implementation(Dependencies.App.legacySupportV4)
    implementation(Dependencies.App.recyclerView)
    implementation(Dependencies.App.navigationFragment)
    implementation(Dependencies.App.navigationUi)
    implementation(Dependencies.App.material)
    implementation(Dependencies.App.lifecycle)
    kapt(Dependencies.App.lifecycleCompiler)
    implementation(Dependencies.App.inject)
    implementation(Dependencies.App.dagger)
    kapt(Dependencies.App.daggerCompiler)
    implementation(Dependencies.App.glide)
    kapt(Dependencies.App.glideCompiler)
    implementation(Dependencies.App.materialProgressBar)
    implementation(Dependencies.App.paging)
    implementation(Dependencies.App.shimmer)
    implementation(Dependencies.App.klaster)
    testImplementation(Dependencies.AppTest.junit)
    testImplementation(Dependencies.AppTest.truth)
    implementation(project(":data"))
}
