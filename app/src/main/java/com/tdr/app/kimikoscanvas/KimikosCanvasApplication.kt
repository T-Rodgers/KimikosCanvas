@file:Suppress("KotlinDeprecation")

package com.tdr.app.kimikoscanvas

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.canvas.DetailsViewModel
import com.tdr.app.kimikoscanvas.data.CanvasDataSource
import com.tdr.app.kimikoscanvas.data.CanvasRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

@ExperimentalCoroutinesApi
class KimikosCanvasApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Call to allow firebase to save retrieved data to device when offline (data-caching)
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        /**
         * use Koin Library as a service locator
         */
        val viewModelModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                CanvasViewModel(
                    get(), get() as CanvasDataSource
                )
            }

            single { DetailsViewModel(get(), get() as CanvasDataSource) }
        }

        val repositoryModule = module {
            fun provideFirebaseDatabase(): FirebaseDatabase {

                return FirebaseDatabase.getInstance()
            }
            single { CanvasRepositoryImpl(provideFirebaseDatabase()) as CanvasDataSource }

        }

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@KimikosCanvasApplication)
            modules(listOf(viewModelModule, repositoryModule))
        }
    }

}


