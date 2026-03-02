package com.kantek.dancer.booking.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.SpaceVertical

@Composable
fun FindDancerScreen() = ScopeProvider(Scopes.Search) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ActionBarMainView(R.string.top_bar_find_dancer)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SpaceVertical(10.dp)
                    Text(
                        text = stringResource(R.string.lbl_find_option),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    )
                    SpaceVertical(10.dp)

                    AppButton(
                        nameRes = R.string.all_select_lawyer,
                        backgroundColor = Colors.Blue241,
                        textColor = Colors.Primary,
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(),
                        onClick = { appNavigator.navigateLawyerList() }
                    )

                    SpaceVertical(20.dp)

                    AppButton(
                        nameRes = R.string.all_quick_request,
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(),
                        onClick = { appNavigator.navigateQuickRequest() }
                    )

                    SpaceVertical(10.dp)

                }
            }

        }
    }

}