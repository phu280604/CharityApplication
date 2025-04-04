package com.developing.charityapplication.domain.repoInter.identityRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.identityModel.UserM

interface IUserRepo {

    // region --- Methods ---

    suspend fun createAccount(userInfo: RequestCreateUser): ResponseM<UserM>?

    // endregion

}