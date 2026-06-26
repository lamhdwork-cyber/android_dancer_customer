package com.kantek.dancer.booking.presentation.widget
import android.support.ui.extension.rememberDebouncedClick
import android.support.ui.extension.onClick

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.Command
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun ActionBarBackAndTitleView(
    textRes: Int = R.string.app_name,
    backgroundColor: Color = Colors.Dark120812,
    onCommand: (Command) -> Unit = {}
) {
    ActionBarBackAndTitleView(stringResource(textRes), backgroundColor, onCommand)
}


@Composable
fun ActionBarBackAndTitleView(
    text: String = stringResource(R.string.app_name),
    backgroundColor: Color = Colors.Dark120812,
    onCommand: (Command) -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                WindowInsets.statusBars.union(WindowInsets.displayCutout)
            )
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .onClick(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = true,
                            color = Colors.Pink66F425F4
                        )
                    ) { onCommand(Command.ActionBarBack) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 56.dp, end = 56.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun ActionBarMainView(
    textRes: Int = R.string.nav_home,
    iconRight: ImageVector? = null,
    onClickIconRight: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                WindowInsets.statusBars.union(WindowInsets.displayCutout)
            ),
        color = Colors.Dark120812, shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .background(Colors.Dark120812)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(textRes),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            if (iconRight != null) {
                IconButton(
                    onClick = rememberDebouncedClick { onClickIconRight?.invoke() },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 4.dp)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = iconRight,
                        contentDescription = null,
                        tint = Colors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ActionBarDancerAdmin(
    clubId: String,
    clubName: String,
    clubAddress: String,
    fallbackTitleRes: Int = R.string.top_bar_status_board,
) {
    val configuration = LocalConfiguration.current
    val addressTextMaxWidth = remember(configuration.screenWidthDp) {
        val raw = configuration.screenWidthDp.dp - 32.dp - 18.dp - 4.dp
        if (raw < 48.dp) 48.dp else raw
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                WindowInsets.statusBars.union(WindowInsets.displayCutout)
            ),
        color = Colors.Dark120812,
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (clubId.isNotBlank() && clubName.isNotBlank()) {
                Text(
                    text = clubName,
                    color = Colors.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = Colors.Primary,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(18.dp),
                        )
                        Text(
                            text = clubAddress,
                            color = Colors.Gray9CA3AF,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.widthIn(max = addressTextMaxWidth),
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(fallbackTitleRes),
                    color = Colors.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}


