package com.developing.charityapplication.di.module

import com.developing.charityapplication.data.api.APIUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIServiceModule {

    // region --- Methods ---

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit) : APIUserService{
        return retrofit.create(APIUserService::class.java)
    }

    // endregion

}