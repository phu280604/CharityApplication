package com.developing.charityapplication.domain.model

data class RoleModel(
    var description: String,
    var name: String,
    var permissions: List<PermissionModel>
)