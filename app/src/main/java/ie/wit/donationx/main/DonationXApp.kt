package ie.wit.donationx.main

import android.app.Application
import timber.log.Timber

class Rove2App : Application() {

    //lateinit var visitStore: VisitStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
      //  visitStore = VisitManager()
        Timber.i("Rove2 Application Started")
    }
}