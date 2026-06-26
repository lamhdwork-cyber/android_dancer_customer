package com.kantek.dancer.booking.presentation.widget
import android.support.ui.extension.rememberDebouncedClick
import android.support.ui.extension.onClick

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.presentation.navigation.AppBottomNavigationScreen
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun AppNavigateBottomBar(
    selectedItemRouter: String,
    onItemRouterSelected: (String) -> Unit,
    firstTab: AppBottomNavigationScreen? = AppBottomNavigationScreen.Search
) {
    val items = listOfNotNull(
        firstTab,
        AppBottomNavigationScreen.Cases,
        AppBottomNavigationScreen.Notification,
        AppBottomNavigationScreen.Account
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Dark120812)
            .navigationBarsPadding()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = Color.Transparent, shadowElevation = 4.dp
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Colors.White1AFFFFFF
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.Dark120812)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach {
                val isSelected = (it.route == selectedItemRouter)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .onClick(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = false),
                            onClick = rememberDebouncedClick { onItemRouterSelected(it.route) })
                        .padding(vertical = 4.dp, horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .width(52.dp)
                            .background(
                                color = if (isSelected) Colors.Pink33F425F4 else Color.Transparent,
                                shape = RoundedCornerShape(18.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.titleRes),
                            tint = if (isSelected) Color.White else Colors.Dark64748B,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = stringResource(it.titleRes).uppercase(),
                        color = if (isSelected) Colors.Primary else Colors.Dark64748B,
                        fontSize = bottomBarFontSize(),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Composable
fun bottomBarFontSize(): TextUnit {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    return when {
        screenWidth < 360 -> 7.sp
        screenWidth < 400 -> 8.sp
        screenWidth < 600 -> 9.sp
        else -> 10.sp
    }
}


