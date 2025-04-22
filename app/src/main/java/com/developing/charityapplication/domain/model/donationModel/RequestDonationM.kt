package com.developing.charityapplication.domain.model.donationModel

data class RequestDonationM(
    val postId: String,
    val donorId: String,
    val amount: String,
    val message: String,
    val paymentMethod: String = "MoMo",
    val anonymous: String = "false",
)

data class ResponseDonationM(
    val amount: Int,
    val anonymous: Boolean,
    val createdAt: String,
    val donorId: String,
    val id: String,
    val message: String,
    val paidAt: Any,
    val payUrl: String,
    val paymentMethod: String,
    val paymentRefId: Any,
    val postId: String,
    val status: String
)