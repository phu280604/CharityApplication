package com.developing.charityapplication.data.api.postsService

import com.developing.charityapplication.domain.model.postModel.RequestPostContentM
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.postModel.ResponsePostsByProfileId
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PostsAPI {

    // region --- GET ---

    // Getting All Posts
    @Headers("Accept: */*")
    @GET("post/postUsers/getAllPosts")
    suspend fun getAllPosts() : Response<ResponseM<List<ResponsePostM>>>

    // Getting All Posts By Profile Id
    @Headers("Accept: */*")
    @GET("post/postUsers/getPostsByProfileId/{profileId}")
    suspend fun getPostsByProfileId(@Path("profileId") profileId: String) : Response<ResponseM<ResponsePostsByProfileId>>

    // endregion

    // region --- POST ---

    // Update Profile
    @Headers("Accept: */*")
    @Multipart
    @POST("post/postUsers/create")
    suspend fun createPost(
        @Part("postCreationRequest") postRequest: RequestPostContentM,
        @Part files: List<MultipartBody.Part>
    ) : Response<ResponseM<ResponsePostM>>

    // endregion

    // region --- PUT ---

    @Headers("Accept: */*")
    @Multipart
    @PUT("post/postUsers/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: String,
        @Query("filesToRemove") filesToRemove: List<String>,
        @Part("postUpdateRequest") postUpdateRequest: RequestPostContentM,
        @Part files: List<MultipartBody.Part>?
    ): Response<ResponseM<ResponsePostM>>

    // endregion

    // region --- DELETE ---

    @Headers("Accept: */*")
    @DELETE("post/postUsers/{postId}")
    suspend fun deletePost(@Path("postId") postId: String) : Response<ResponseM<String>>


    // endregion

}