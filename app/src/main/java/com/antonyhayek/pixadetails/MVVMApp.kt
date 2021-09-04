package com.antonyhayek.pixadetails

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.antonyhayek.pixadetails.data.local.prefsstore.PrefsStore
import com.antonyhayek.pixadetails.data.remote.ApiInterface
import com.antonyhayek.pixadetails.data.remote.MockApiInterface
import com.antonyhayek.pixadetails.data.remote.MockInterceptor
import com.antonyhayek.pixadetails.data.remote.NetworkConnectionInterceptor
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.antonyhayek.pixadetails.data.repositories.LoginRepository
import com.antonyhayek.pixadetails.data.repositories.RegisterRepository
import com.antonyhayek.pixadetails.ui.details.DetailsViewModelFactory
import com.antonyhayek.pixadetails.ui.home.HomeViewModelFactory
import com.antonyhayek.pixadetails.ui.home.ImagePaginationDataSource
import com.antonyhayek.pixadetails.ui.login.LoginViewModelFactory
import com.antonyhayek.pixadetails.ui.register.RegisterViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MVVMApp : Application(), KodeinAware {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pixa_details_data_store")

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from singleton { HomeViewModelFactory(instance()) }

        bind() from singleton { MockApiInterface(instance()) }
        bind() from singleton { LoginViewModelFactory(instance()) }
        bind() from singleton { LoginRepository(instance(), instance()) }
        bind() from singleton { MockInterceptor() }

        bind() from singleton { RegisterRepository(instance()) }
        bind() from singleton { RegisterViewModelFactory(instance()) }

        bind() from singleton { ImagePaginationDataSource(instance()) }

        bind() from singleton { PrefsStore(dataStore) }

        bind() from singleton { DetailsViewModelFactory(instance()) }

    }


}