package com.developing.charityapplication.domain.model.role

import com.developing.charityapplication.domain.model.permission.PermissionM

data class RoleM(
    var description: String,
    var name: String,
    var permissions: List<PermissionM>
)