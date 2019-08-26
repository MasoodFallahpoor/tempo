package ir.fallahpoor.tempo.app

import android.app.Application
import ir.fallahpoor.tempo.app.di.AppComponent
import ir.fallahpoor.tempo.app.di.AppModule
import ir.fallahpoor.tempo.app.di.DaggerAppComponent

class TempoApplication : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

}