package com.developing.charityapplication.domain.repository

import com.developing.charityapplication.domain.model.RequestCreateUser
import com.developing.charityapplication.domain.model.ResponseModel
import com.developing.charityapplication.domain.model.UserModel

interface IUserRepo {

    // region --- Methods ---

    suspend fun createAccount(userInfo: RequestCreateUser): ResponseModel<UserModel>?

    // endregion

}