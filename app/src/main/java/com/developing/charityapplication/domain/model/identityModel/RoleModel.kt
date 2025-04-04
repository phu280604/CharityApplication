package com.developing.charityapplication.domain.model.identityModel

data class RoleM(
    var description: String,
    var name: String,
    var permissions: List<PermissionM>
)