package com.developing.charityapplication.data.repository.donationRepo

import android.util.Log
import com.developing.charityapplication.data.api.donationService.DonationAPI
import com.developing.charityapplication.data.api.identityService.AuthAPI
import com.developing.charityapplication.domain.model.donationModel.RequestDonationM
import com.developing.charityapplication.domain.model.donationModel.ResponseDonationM
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.donationRepoInter.IDonationRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import javax.inject.Inject

class DonationRepo @Inject constructor(
    private val apiDonation : DonationAPI
): IDonationRepo {

    // region --- Overrides ---

    override suspend fun createDonation(donationRequest: RequestDonationM): ResponseM<ResponseDonationM>? {
        try {
            Log.d("Json", Gson().toJson(donationRequest).toString())
            val response = apiDonation.createDonation(donationRequest)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResponseDonationM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun getTotalDonateByPostId(postId: String): ResponseM<Int>? {
        try {
            Log.d("Json", Gson().toJson(postId).toString())
            val response = apiDonation.getTotalDonateByPostId(postId)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<Int> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    // endregion
}