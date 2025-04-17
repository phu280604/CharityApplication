package com.developing.charityapplication.data.api.profileService

import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserProfilesAPI {

    // region --- GET ---

    // Getting Profiles
    @Headers("Accept: */*")
    @GET("profile/userProfiles/myProfiles")
    suspend fun getProfilesUser() : Response<ResponseM<List<ResponseProfilesM>>>

    @Headers("Accept: */*")
    @GET("profile/userProfiles/{profileId}")
    suspend fun getProfileByProfileId(@Path("profileId") profileId: String) : Response<ResponseM<ResponseProfilesM>>

    // endregion

    // region --- POST ---

    // Setting Profile Active
    @Headers("Accept: */*")
    @POST("profile/userProfiles/set-active-profile")
    suspend fun setActiveProfile(@Query("profileId") profileId: String) : Response<ResponseM<String>>

    // endregion

    // region --- PUT ---

    // Update Profile
    @Headers("Accept: */*")
    @Multipart
    @PUT("profile/userProfiles/{profileId}")
    suspend fun updateProfileUser(
        @Path("profileId") profileId: String,
        @Part("profile") profile: RequestUpdateProfileM,
        @Part avatar: MultipartBody.Part?
    ) : Response<ResponseM<ResponseProfilesM>>

    // endregion
}