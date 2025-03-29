package com.developing.charityapplication.di.module

import com.developing.charityapplication.data.repository.UserRepository
import com.developing.charityapplication.domain.repository.IUserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    // region --- Methods ---

    @Provides
    fun providesUserVM(repo: UserRepository): IUserRepo = repo

    // endregion

}