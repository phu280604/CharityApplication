package com.developing.charityapplication.di.module.repo

import com.developing.charityapplication.data.repository.identityRepo.AuthRepo
import com.developing.charityapplication.data.repository.identityRepo.UsersRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
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
    fun providesUsersRepo(repo: UsersRepo): IUserRepo = repo

    @Provides
    fun providesAuthRepo(repo: AuthRepo): IAuthRepo = repo

    // endregion

}