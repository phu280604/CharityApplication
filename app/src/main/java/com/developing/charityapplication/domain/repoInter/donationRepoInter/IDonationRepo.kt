package com.developing.charityapplication.domain.repoInter.donationRepoInter

import com.developing.charityapplication.domain.model.donationModel.RequestDonationM
import com.developing.charityapplication.domain.model.donationModel.ResponseDonationM
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
import retrofit2.http.Query

interface IDonationRepo {

    // region --- Methods ---

    suspend fun createDonation(donationRequest: RequestDonationM) : ResponseM<ResponseDonationM>?

    suspend fun getTotalDonateByPostId(postId: String) : ResponseM<Int>?

    // endregion
}