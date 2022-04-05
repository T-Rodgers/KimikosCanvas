package com.tdr.app.kimikoscanvas

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.data.firebase.CanvasRepositoryImpl
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
         * Call to allow firebase to save database reference to device for when Firebase is offline
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        /**
         * use Koin Library as a service locator
         */
        val viewModelModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                CanvasViewModel(
                    get(), get()
                )
            }
        }

        val repositoryModule = module {
            fun provideFirebaseDatabase(): FirebaseDatabase {

                return FirebaseDatabase.getInstance()
            }
            single { CanvasRepositoryImpl(provideFirebaseDatabase()) }
        }

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@KimikosCanvasApplication)
            modules(listOf(viewModelModule, repositoryModule))
        }
    }

}


