package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import javax.inject.Inject

class ButtonContentAlignment @Inject constructor(
    var config: ButtonConfig
) : IButtonContentAlignment {

    @Composable
    override fun HorizontalAlignment() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            ContentIconButton(config.iconRes)

            ContentImageButton(config.imageRes)

            if (!config.text.isEmpty())
                ContentTextButton(
                    text = config.text,
                    textStyle = config.textStyle
                )
        }
    }

    @Composable
    override fun VerticalAlignment() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContentIconButton(config.iconRes)

            ContentImageButton(config.imageRes)

            if (!config.text.isEmpty())
                ContentTextButton(
                    text = config.text,
                    textStyle = config.textStyle
                )
        }
    }

    @Composable
    fun ContentIconButton(
        iconRes: Int?
    ){
        if(iconRes != null){
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    @Composable
    fun ContentImageButton(
        imageRes: Int?
    ){
        if(imageRes != null){
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
    }

    @Composable
    fun ContentTextButton(
        text: String,
        textStyle: TextStyle
    ){
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = textStyle
        )
    }

}