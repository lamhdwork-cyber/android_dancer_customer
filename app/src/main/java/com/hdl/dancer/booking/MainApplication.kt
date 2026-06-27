package com.hdl.dancer.booking

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.hdl.dancer.booking.app.apiModule
import com.hdl.dancer.booking.app.conversation
import com.hdl.dancer.booking.app.dataModule
import com.hdl.dancer.booking.app.domainModule
import com.hdl.dancer.booking.app.networkModule
import com.hdl.dancer.booking.app.presentationModule
import com.hdl.dancer.booking.app.serializeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.Locale


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.PLACE_API_KEY, Locale.getDefault())
        }

        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(
                presentationModule,
                dataModule,
                domainModule,
                serializeModule,
                networkModule,
                apiModule,
                conversation
            )
        }
    }
}
