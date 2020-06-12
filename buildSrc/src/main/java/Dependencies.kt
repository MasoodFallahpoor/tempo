object Dependencies {

    private const val navigationVersion = "2.3.0-rc1"
    private const val kotlinVersion = "1.3.72"
    private const val retrofitVersion = "2.9.0"
    private const val gsonVersion = "2.8.6"
    private const val injectVersion = "1"
    private const val daggerVersion = "2.25.2"
    private const val lifecycleVersion = "2.2.0-rc01"
    private const val glideVersion = "4.10.0"
    private const val rxJavaVersion = "2.2.17"

    object App {
        const val navigationFragment =
            "android.arch.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "android.arch.navigation:navigation-ui-ktx:$navigationVersion"
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val legacySupportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
        const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-beta01"
        const val inject = "javax.inject:javax.inject:$injectVersion"
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
        const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
        const val material = "com.google.android.material:material:1.2.0-alpha01"
        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
        const val materialProgressBar = "me.zhanghai.android.materialprogressbar:library:1.6.1"
        const val klaster = "com.github.rongi:klaster:0.3.5"
    }

    object Data {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitJsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val retrofitRxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
        const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc01"
        const val inject = "javax.inject:javax.inject:$injectVersion"
    }

    object DataTest {
        const val robolectric = "org.robolectric:robolectric:4.3.1"
        const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:$retrofitVersion"
    }

    object CommonTest {
        const val mockito = "org.mockito:mockito-inline:3.0.0"
        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val testCore = "androidx.test:core:1.2.0"
        const val junit = "androidx.test.ext:junit:1.1.1"
        const val truth = "androidx.test.ext:truth:1.2.0"
    }

}