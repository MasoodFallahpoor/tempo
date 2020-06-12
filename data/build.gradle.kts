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
    implementation(Dependencies.Data.kotlin)
    implementation(Dependencies.Data.retrofit)
    implementation(Dependencies.Data.retrofitJsonConverter)
    implementation(Dependencies.Data.retrofitRxJava2Adapter)
    implementation(Dependencies.Data.rxJava)
    implementation(Dependencies.Data.gson)
    implementation(Dependencies.Data.livedata)
    implementation(Dependencies.Data.inject)
    testImplementation(Dependencies.CommonTest.junit)
    testImplementation(Dependencies.CommonTest.truth)
    testImplementation(Dependencies.CommonTest.testCore)
    testImplementation(Dependencies.CommonTest.mockito)
    testImplementation(Dependencies.CommonTest.archCoreTesting)
    testImplementation(Dependencies.DataTest.retrofitMock)
    testImplementation(Dependencies.DataTest.robolectric)
}
