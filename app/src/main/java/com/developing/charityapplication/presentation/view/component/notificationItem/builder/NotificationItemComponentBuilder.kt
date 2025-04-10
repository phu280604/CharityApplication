package com.developing.charityapplication.presentation.view.component.notificationItem.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldComponent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.decorator.IInputFieldComponentDecorator
import com.developing.charityapplication.presentation.view.component.notificationItem.NotificationConfig
import com.developing.charityapplication.presentation.view.component.notificationItem.NotificationItemComponent
import com.developing.charityapplication.presentation.view.component.notificationItem.decorator.INotificationItemComponentDecorator

class NotificationItemComponentBuilder : IBaseBuilder<INotificationItemComponentDecorator> {
    private var config = NotificationConfig()

    fun withConfig(newConfig: NotificationConfig) = apply { this.config = newConfig }

    override fun build(): INotificationItemComponentDecorator = NotificationItemComponent(config)
}