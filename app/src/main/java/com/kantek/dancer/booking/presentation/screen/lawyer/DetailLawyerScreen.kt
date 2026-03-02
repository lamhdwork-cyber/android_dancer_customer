package com.kantek.dancer.booking.presentation.screen.lawyer

import android.support.core.extensions.safe
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kantek.dancer.booking.app.AppSettings
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.factory.BookingFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.ILawyerDetail
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.copyText
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.BoxText
import com.kantek.dancer.booking.presentation.widget.CallPhoneDialog
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import com.kantek.dancer.booking.presentation.widget.TagList
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailLawyerScreen(
    lawyerID: Int = -1,
    dataJson: String = "",//BookingDTO::class
    viewModel: DetailLawyerVM = koinViewModel()
) = ScopeProvider(Scopes.Lawyer) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)
    val appSetting = remember { AppSettings(context) }

    val detail by viewModel.details.collectAsState()
    var hasShowCallDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.setData(lawyerID, dataJson)
    }
    PermissionProvider {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionBarBackAndTitleView(R.string.top_bar_lawyer_detail) { appNavigator.back() }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (detail != null) {
                    SpaceVertical(10.dp)
                    AvatarImage(
                        detail?.avatarURL,
                        80.dp
                    ) { appNavigator.navigatePhotoViewer(detail!!.avatarURL) }
                    SpaceVertical(10.dp)

                    Text(
                        text = detail!!.fullName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    SpaceVertical(10.dp)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BoxText(stringResource(R.string.exp_s, detail!!.exp))
                        BoxText(stringResource(R.string.cases_s, detail!!.cases))
                    }
                    SpaceVertical(12.dp)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )
                    SpaceVertical(20.dp)

                    if (!detail!!.specialties.isNullOrEmpty()) {
                        Text(
                            text = stringResource(R.string.all_lawyer_specialties),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SpaceVertical(10.dp)
                        TagList(detail!!.specialties!!)

                        SpaceVertical(12.dp)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )
                        SpaceVertical(20.dp)
                    }

                    if (detail!!.education.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.all_education),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SpaceVertical(10.dp)
                        Text(
                            text = detail!!.education,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )

                        SpaceVertical(12.dp)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )
                        SpaceVertical(20.dp)
                    }

                    Text(
                        text = stringResource(R.string.all_achievements),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SpaceVertical(10.dp)
                    Text(
                        text = detail!!.achievements,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    SpaceVertical(12.dp)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )
                    SpaceVertical(20.dp)

                    Text(
                        text = stringResource(R.string.all_lawyer_license),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SpaceVertical(10.dp)
                    Row(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .clickable { appNavigator.navigatePhotoViewer(detail!!.licenseURL) }
                            .background(
                                color = Colors.Blue241,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_file),
                            contentDescription = "License",
                            modifier = Modifier.size(15.dp),
                            tint = Colors.Primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.all_view_license),
                            color = Colors.Primary,
                            fontSize = 14.sp
                        )
                    }

                    SpaceVertical(12.dp)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )

                    if (detail!!.phoneDisplay.isNotEmpty() && detail!!.email.isNotEmpty()) {
                        SpaceVertical(20.dp)

                        Text(
                            text = stringResource(R.string.all_contact_info),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SpaceVertical(10.dp)
                        Row(
                            modifier = Modifier
                                .clickable {
                                    accessCallPhone { hasShowCallDialog = true }
                                }
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_phone),
                                contentDescription = "License",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = detail!!.phoneDisplay,
                                color = Colors.Primary,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { context.copyText(detail!!.phoneNumber) }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_copy),
                                    contentDescription = "Copy",
                                    tint = Colors.Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.all_copy),
                                    color = Colors.Primary,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )

                        Row(
                            modifier = Modifier
                                .clickable { appSetting.sendEmail(detail!!.email) }
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_mail),
                                contentDescription = "License",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = detail!!.email,
                                color = Colors.Primary,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { context.copyText(detail!!.email) }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_copy),
                                    contentDescription = "Copy",
                                    tint = Colors.Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.all_copy),
                                    color = Colors.Primary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        SpaceVertical(12.dp)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )
                    }
                    SpaceVertical(20.dp)
                    //review
                    Text(
                        text = stringResource(R.string.all_reviews),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SpaceVertical(10.dp)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${detail?.rating} (${detail?.reviewCount})",
                            fontSize = 14.sp
                        )

                        SpaceHorizontal(20.dp)

                        AppButton(
                            modifier = Modifier
                                .height(35.dp)
                                .wrapContentWidth(),
                            backgroundColor = Colors.Blue241,
                            textColor = Colors.Primary,
                            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                            nameRes = R.string.all_review_list,
                            onClick = {
                                appNavigator.navigateReviewList(
                                    detail?.id.safe(),
                                    "${detail?.rating} (${detail?.reviewCount})"
                                )
                            }
                        )
                    }

                    SpaceVertical(20.dp)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )

                    if (lawyerID != -1 && dataJson.isEmpty()) {
                        SpaceVertical(14.dp)
                        AppButton(R.string.all_request) { appNavigator.navigateQuickRequest(detail?.owner.toJson()) }
                    }
                } else NoDataView(htmlRes = R.string.no_data)
                if (hasShowCallDialog) {
                    CallPhoneDialog(
                        phoneNumber = detail!!.phoneDisplay,
                        onConfirm = {
                            hasShowCallDialog = false
                            appSetting.call(detail!!.phoneNumber)
                        }, onDismiss = {
                            hasShowCallDialog = false
                        }
                    )
                }
            }
        }
    }
}

class DetailLawyerVM(
    private val getLawyerDetailRepo: GetLawyerDetailRepo,
    private val fetchDetailLawyerRepo: FetchDetailLawyerRepo
) : AppViewModel() {
    val details = MutableStateFlow<ILawyerDetail?>(null)

    fun setData(id: Int, dataJson: String) = launch(loading, error) {
        details.emit(
            if (id != -1 && id != 0)
                fetchDetailLawyerRepo(id)
            else getLawyerDetailRepo(dataJson)
        )
    }

}

class FetchDetailLawyerRepo(
    private val lawyerApi: LawyerApi,
    private val bookingFactory: BookingFactory
) {
    suspend operator fun invoke(id: Int): ILawyerDetail {
        return bookingFactory.createLawyerDetail(lawyerApi.detail(id).await())
    }

}

class GetLawyerDetailRepo(private val bookingFactory: BookingFactory) {

    suspend operator fun invoke(dataJson: String): ILawyerDetail? {
        return bookingFactory.createLawyerDetail(dataJson)
    }

}
