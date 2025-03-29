package com.developing.charityapplication.domain.repository

import com.developing.charityapplication.domain.model.user.RequestCreateUser
import com.developing.charityapplication.domain.model.ResponseM
import com.developing.charityapplication.domain.model.user.UserM

interface IUserRepo {

    // region --- Methods ---

    suspend fun createAccount(userInfo: RequestCreateUser): ResponseM<UserM>?

    // endregion

}