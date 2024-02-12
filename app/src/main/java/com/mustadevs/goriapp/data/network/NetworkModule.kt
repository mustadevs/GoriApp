package com.mustadevs.goriapp.data.network

import com.mustadevs.goriapp.data.RepositoryImpl
import com.mustadevs.goriapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://newastro.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideBuyApiService(retrofit: Retrofit): BuyApiService{
        return retrofit.create(BuyApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: BuyApiService): Repository{
        return RepositoryImpl(apiService)
    }
}