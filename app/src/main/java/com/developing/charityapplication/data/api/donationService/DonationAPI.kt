package com.developing.charityapplication.data.api.donationService

import com.developing.charityapplication.domain.model.donationModel.RequestDonationM
import com.developing.charityapplication.domain.model.donationModel.ResponseDonationM
import com.developing.charityapplication.domain.model.identityModel.AuthenticationM
import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestLogoutM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.RequestResetPasswordM
import com.developing.charityapplication.domain.model.identityModel.ResponseVerifyResetPasswordM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DonationAPI {

    // region --- Methods ---

    // Create Donation
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("donation/userDonation")
    suspend fun createDonation(@Body donationRequest: RequestDonationM): Response<ResponseM<ResponseDonationM>>

    // Get total donation by post Id
    @Headers("Content-Type: application/json", "Accept: */*")
    @GET("donation/userDonation/total/{postId}")
    suspend fun getTotalDonateByPostId(@Path("postId") postId: String): Response<ResponseM<Int>>

    // endregion

}