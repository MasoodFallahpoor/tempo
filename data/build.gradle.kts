plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

//    testOptions {
//        unitTests {
//            includeAndroidResources = true
//        }
//    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation(Dependencies.Data.kotlin)
    implementation(Dependencies.Data.retrofit)
    implementation(Dependencies.Data.retrofitJsonConverter)
    implementation(Dependencies.Data.gson)
    implementation(Dependencies.Data.livedata)
    implementation(Dependencies.Data.inject)
    implementation(Dependencies.Data.paging)
    implementation(Dependencies.Data.coroutines)
    testImplementation(Dependencies.DataTest.junit)
    testImplementation(Dependencies.DataTest.truth)
    testImplementation(Dependencies.DataTest.testCore)
    testImplementation(Dependencies.DataTest.robolectric)
    testImplementation(Dependencies.DataTest.mockito)
    testImplementation(Dependencies.DataTest.archCoreTesting)
    testImplementation(Dependencies.DataTest.retrofitMock)
}
