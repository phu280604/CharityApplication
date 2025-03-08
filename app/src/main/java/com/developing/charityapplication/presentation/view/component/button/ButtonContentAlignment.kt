package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.ComponentContentAlignment
import javax.inject.Inject

class ButtonContentAlignment @Inject constructor(
    var config: ButtonConfig
) : ComponentContentAlignment {

    @Composable
    override fun ContentAlignment() {
        if(config.isRow){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                ContentIconButton(
                    isIcon =  config.isIcon,
                    iconRes =  config.iconRes
                )

                ContentTextButton(
                    text = config.text,
                    textStyle = config.textStyle
                )
            }
            return
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContentIconButton(
                isIcon =  config.isIcon,
                iconRes =  config.iconRes
            )

            ContentTextButton(
                text = config.text,
                textStyle = config.textStyle
            )
        }
    }

    @Composable
    fun ContentIconButton(
        isIcon: Boolean,
        iconRes: Int?
    ){
        if(isIcon && iconRes != null){
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }

    @Composable
    fun ContentTextButton(
        text: String,
        textStyle: TextStyle
    ){
        Text(
            text = text,
            style = textStyle
        )
    }

}