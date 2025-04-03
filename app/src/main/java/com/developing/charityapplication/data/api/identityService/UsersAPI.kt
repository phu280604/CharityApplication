package com.developing.charityapplication.data.api.identityService

import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.domain.model.identityModel.UserM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import retrofit2.Response
import retrofit2.http.*

interface UsersAPI {

    // region --- POST ---

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/users/registration")
    suspend fun createUser(@Body userRequest: RequestCreateUser) : Response<ResponseM<UserM>>

    // endregion

}