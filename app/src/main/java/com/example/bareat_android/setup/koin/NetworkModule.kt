package com.example.bareat_android.setup.koin

import com.example.bareat_android.setup.client.BareatClientImpl
import com.example.domain.client.BareatClient
import com.example.domain.client.BareatService
import com.example.domain.error.NetworkController
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {


    single {
        provideRetrofit()
    }

    single<BareatClient> { BareatClientImpl(get(), get()) }

    single { NetworkController() }

}

private fun provideRetrofit(): BareatService = Retrofit.Builder()
        .baseUrl(BareatService.BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(BareatService::class.java)