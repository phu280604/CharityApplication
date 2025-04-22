package com.developing.charityapplication.di.module.repo

import com.developing.charityapplication.data.repository.donationRepo.DonationRepo
import com.developing.charityapplication.data.repository.identityRepo.AuthRepo
import com.developing.charityapplication.data.repository.identityRepo.UsersRepo
import com.developing.charityapplication.data.repository.postRepo.PostRepo
import com.developing.charityapplication.data.repository.profileRepo.ProfileRepo
import com.developing.charityapplication.domain.repoInter.donationRepoInter.IDonationRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IUserRepo
import com.developing.charityapplication.domain.repoInter.postsRepoInter.IPostRepo
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
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

    @Provides
    fun providesProfileRepo(repo: ProfileRepo): IProfileRepo = repo

    @Provides
    fun providesPostRepo(repo: PostRepo): IPostRepo = repo

    @Provides
    fun providesDonationRepo(repo: DonationRepo): IDonationRepo = repo

    // endregion

}