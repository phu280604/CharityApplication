//package com.developing.charityapplication.data.api.identityService
//
//import com.developing.charityapplication.domain.model.identityModel.AuthenticationM
//import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
//import com.developing.charityapplication.domain.model.identityModel.ResultLoginM
//import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.Headers
//import retrofit2.http.POST
//
//interface AuthAPI {
//
//    // region --- Methods ---
//
//    @Headers("Content-Type: application/json", "Accept: */*")
//    @POST("identity/auth/token")
//    suspend fun loginService(@Body authRequest: RequestLoginM): Response<ResponseM<ResultLoginM>>
//
//    // endregion
//
//}