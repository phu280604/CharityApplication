package com.developing.charityapplication.domain.model.identityModel

import java.util.Date

data class UserM(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var email: String = "",
    var dob: Date? = null,
    var roles: List<RoleM>? = null,
    var provider: String = "",
    var blocked: Boolean = false,
)

data class RequestCreateUser(
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var dob: Date? = null
)