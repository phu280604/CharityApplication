package com.developing.charityapplication.domain.repoInter.identityRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.ResultLoginM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM

interface IAuthRepo {

    // region --- Methods ---

    suspend fun login(loginInfo: RequestLoginM) : ResponseM<ResultLoginM>?

    // endregion
}