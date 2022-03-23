package com.tdr.app.kimikoscanvas

import android.app.Application
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.data.CanvasDataSource
import com.tdr.app.kimikoscanvas.data.CanvasesRepository
import com.tdr.app.kimikoscanvas.data.LocalDB
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class KimikosCanvasApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                CanvasViewModel(
                    get(),
                    get() as CanvasDataSource
                )
            }

            single { CanvasesRepository(get()) as CanvasDataSource }
            single { LocalDB.createCanvasesDao(this@KimikosCanvasApplication) }
        }

        startKoin {
            androidContext(this@KimikosCanvasApplication)
            modules(listOf(myModule))
        }

        Timber.plant(Timber.DebugTree())
    }


}