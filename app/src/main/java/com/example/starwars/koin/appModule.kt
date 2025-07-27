package com.example.starwars.koin

import com.example.starwars.networking.instance.Instance
import com.example.starwars.utils.network.ConnectivityObserver
import com.example.starwars.utils.network.NetworkConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Instance.api
    }

    single<StarWarsRepository> {
        StarWarsRepositoryImpl(get())
    }

    single<ConnectivityObserver> {
        NetworkConnectivityObserver(androidContext())
    }

}