package com.developing.charityapplication.di.module.serviceAPI

import com.developing.charityapplication.data.api.profileService.UserProfilesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileServiceModule {

    // region --- Methods ---

    @Provides
    @Singleton
    fun provideProfileAPI(retrofit: Retrofit) : UserProfilesAPI{
        return retrofit.create(UserProfilesAPI::class.java)
    }

    // endregion

}