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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Dependencies.App.kotlin)
    implementation(Dependencies.App.appCompat)
    implementation(Dependencies.App.coreKtx)
    implementation(Dependencies.App.fragmentKtx)
    implementation(Dependencies.App.constraintLayout)
    implementation(Dependencies.App.legacySupportV4)
    implementation(Dependencies.App.rxJava)
    implementation(Dependencies.App.rxAndroid)
    implementation(Dependencies.App.recyclerView)
    implementation(Dependencies.App.navigationFragment)
    implementation(Dependencies.App.navigationUi)
    implementation(Dependencies.App.material)
    implementation(Dependencies.App.lifecycle)
    kapt(Dependencies.App.lifecycleCompiler)
    implementation(Dependencies.App.inject)
    implementation(Dependencies.App.dagger)
    kapt(Dependencies.App.daggerCompiler)
    implementation(Dependencies.App.coil)
    implementation(Dependencies.App.materialProgressBar)
    implementation(Dependencies.App.klaster)
    testImplementation(Dependencies.CommonTest.mockito)
    testImplementation(Dependencies.CommonTest.testCore)
    testImplementation(Dependencies.CommonTest.junit)
    testImplementation(Dependencies.CommonTest.truth)
    testImplementation(Dependencies.CommonTest.archCoreTesting)
    implementation(project(":data"))
}
