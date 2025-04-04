package com.developing.charityapplication.di.module.repo

import com.developing.charityapplication.data.repository.identityRepo.UsersRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IUserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    // region --- Methods ---

    @Provides
    fun providesUserVM(repo: UsersRepo): IUserRepo = repo

    // endregion

}