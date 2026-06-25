package com.kantek.dancer.booking.presentation.widget

import android.media.MediaPlayer
import android.support.core.extensions.safe
import android.support.ui.widget.AppButton
import android.support.ui.widget.SpaceVertical
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.user.IUser
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun ProfileHeader(it: IUser?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImage(it?.avatarURL)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = it?.fullName.safe(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = it?.email.safe(),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun NoLoginView(
    @StringRes titleRes: Int,
    @StringRes messageRes: Int = R.string.booking_no_yet_des,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(titleRes),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        SpaceVertical(5.dp)
        Text(
            text = stringResource(messageRes),
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        SpaceVertical(16.dp)
        AppButton(
            R.string.all_sign_up_or_sign_in,
            modifier = Modifier.height(55.dp)
        ) { onClick() }
    }
}


@Composable
fun SettingItem(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @StringRes subtitleRes: Int? = null,
    isDanger: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            // Play sound
            val mediaPlayer = MediaPlayer.create(context, R.raw.sound_button)
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
            onClick?.invoke()
            mediaPlayer.start()
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 18.dp, horizontal = 14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(iconRes),
                    contentDescription = stringResource(titleRes),
                    tint = if (isDanger) Colors.Red247 else Color.Black,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(titleRes),
                        fontSize = 16.sp,
                        color = if (isDanger) Colors.Red247 else Color.Black
                    )
                    subtitleRes?.let {
                        Text(
                            text = stringResource(it),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = "Go",
                    tint = Color.Gray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}



