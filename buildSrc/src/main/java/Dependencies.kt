object Dependencies {

    private const val navigationVersion = "2.3.0-rc01"
    private const val kotlinVersion = "1.3.72"
    private const val retrofitVersion = "2.9.0"
    private const val gsonVersion = "2.8.6"
    private const val injectVersion = "1"
    private const val hiltVersion = "2.28-alpha"
    private const val lifecycleVersion = "2.2.0-rc01"
    private const val rxJavaVersion = "2.2.17"

    object App {
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val legacySupportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
        const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-beta01"
        const val inject = "javax.inject:javax.inject:$injectVersion"
        const val hilt =  "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidCompiler =  "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha01"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
        const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
        const val material = "com.google.android.material:material:1.2.0-alpha01"
        const val coil = "io.coil-kt:coil:0.11.0"
        const val materialProgressBar = "me.zhanghai.android.materialprogressbar:library:1.6.1"
        const val klaster = "com.github.rongi:klaster:0.3.5"
    }

    object Data {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitJsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val retrofitRxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
        const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc01"
        const val inject = "javax.inject:javax.inject:$injectVersion"
    }

    object DataTest {
        const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:$retrofitVersion"
    }

    object CommonTest {
        const val robolectric = "org.robolectric:robolectric:4.3.1"
        const val mockito = "org.mockito:mockito-inline:3.0.0"
        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val testCore = "androidx.test:core:1.2.0"
        const val junit = "androidx.test.ext:junit:1.1.1"
        const val truth = "androidx.test.ext:truth:1.2.0"
    }

}