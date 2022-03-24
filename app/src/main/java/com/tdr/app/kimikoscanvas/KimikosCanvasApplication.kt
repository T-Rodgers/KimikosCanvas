package com.tdr.app.kimikoscanvas

import android.app.Application
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class KimikosCanvasApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Call to allow firebase to save database reference to device for when Firebase is offline
         */
        FirebaseUtils().setPersistenceEnabled()

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                CanvasViewModel(
                    get()
                )
            }

        }

        startKoin {
            androidContext(this@KimikosCanvasApplication)
            modules(listOf(myModule))
        }

        Timber.plant(Timber.DebugTree())
    }


}