package com.developing.charityapplication.di.module.serviceAPI

import com.developing.charityapplication.data.api.identityService.UsersAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IdentityServiceModule {

    // region --- Methods ---

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit) : UsersAPI{
        return retrofit.create(UsersAPI::class.java)
    }

    // endregion

}