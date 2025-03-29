package com.developing.charityapplication.data.api

import com.developing.charityapplication.domain.model.*
import com.developing.charityapplication.domain.model.user.RequestCreateUser
import com.developing.charityapplication.domain.model.user.UserM
import retrofit2.Response
import retrofit2.http.*

interface APIUserService {

    // region --- POST ---

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/users")
    suspend fun createUser(@Body userRequest: RequestCreateUser) : Response<ResponseM<UserM>>

    // endregion

}