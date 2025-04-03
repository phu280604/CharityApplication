package com.developing.charityapplication.presentation.view.component.navItem

data class NavItemConfig(
    var title: Int = 0,
    var icon: Int = 0,
    var skipOption: Boolean = false,
    var isSelected: Boolean = true,
    var hasNew: Boolean = false,
    var badgeCount: Int? = null
)