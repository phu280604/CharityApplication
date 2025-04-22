package com.developing.charityapplication.di.module.serviceAPI

import com.developing.charityapplication.data.api.donationService.DonationAPI
import com.developing.charityapplication.data.api.identityService.AuthAPI
import com.developing.charityapplication.data.api.identityService.UsersAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DonationServiceModule {

    // region --- Methods ---

    @Provides
    @Singleton
    fun provideDonationAPI(retrofit: Retrofit) : DonationAPI{
        return retrofit.create(DonationAPI::class.java)
    }

    // endregion

}