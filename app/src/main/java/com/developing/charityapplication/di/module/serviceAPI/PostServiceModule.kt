package com.developing.charityapplication.di.module.serviceAPI

import com.developing.charityapplication.data.api.postsService.PostsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostServiceModule {

    // region --- Methods ---

    @Provides
    @Singleton
    fun providePostsAPI(retrofit: Retrofit) : PostsAPI{
        return retrofit.create(PostsAPI::class.java)
    }

    // endregion

}