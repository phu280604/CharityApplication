package com.developing.charityapplication.data.api

import com.developing.charityapplication.domain.model.*
import retrofit2.Response
import retrofit2.http.*

interface APIUserService {

    // region --- POST ---

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("users")
    suspend fun createUser(@Body userRequest: RequestCreateUser) : Response<ResponseModel<UserModel>>

    // endregion

}