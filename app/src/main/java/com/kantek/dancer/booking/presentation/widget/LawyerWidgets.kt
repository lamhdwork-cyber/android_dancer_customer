package com.kantek.dancer.booking.presentation.widget

import android.support.core.extensions.safe
import android.support.ui.widget.AppButton
import android.support.ui.widget.RatingBar
import android.support.ui.widget.SpaceHorizontal
import android.support.ui.widget.SpaceVertical
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.review.IReview
import com.kantek.dancer.booking.domain.model.user.ILawyer
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun LawyerInfo(
    it: ILawyer,
    hasShowButton: Boolean = false,
    hasShowReview: Boolean = false,
    rating: Float = 5f,
    onDetail: ((Int) -> Unit)? = null,
    onReview: ((Int) -> Unit)? = null,
    onRatingChanged: ((Float) -> Unit)? = null
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.9f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpaceVertical(10.dp)
            AvatarImage(it.avatarURL, 80.dp)
            SpaceVertical(10.dp)

            Text(
                text = it.fullName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            SpaceVertical(10.dp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BoxText(stringResource(R.string.exp_s, it.exp))
                BoxText(stringResource(R.string.cases_s, it.cases))
            }

            SpaceVertical(12.dp)

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Colors.Gray238
            )

            SpaceVertical(12.dp)

            if (!it.specialties.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.all_specialty),
                    fontSize = 12.sp,
                    color = Colors.Primary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                TagList(it.specialties!!)
            }

            if (onRatingChanged != null)
                RatingBar(
                    rating = rating,
                    onRatingChanged = { onRatingChanged(it) }
                )

            if (hasShowButton) {
                SpaceVertical(14.dp)
                AppButton(
                    nameRes = R.string.all_lawyer_detail,
                    backgroundColor = Colors.Blue241,
                    textColor = Colors.Primary,
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth(),
                    fontSize = 14,
                    onClick = { onDetail?.invoke(it.id) }
                )

                if (hasShowReview) {
                    SpaceVertical(10.dp)
                    AppButton(
                        nameRes = R.string.all_leave_review,
                        modifier = Modifier
                            .height(45.dp)
                            .fillMaxWidth(),
                        fontSize = 14,
                        onClick = { onReview?.invoke(it.id) }
                    )
                }
            }
            SpaceVertical(10.dp)
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(tags: List<String>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Colors.Blue247,
                shadowElevation = 0.dp
            ) {
                Text(
                    text = tag,
                    color = Color.Black,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun RatingStars(rating: Float, modifier: Modifier = Modifier, starSize: Dp = 20.dp) {
    val fullStars = rating.toInt()
    val hasHalfStar = (rating - fullStars) >= 0.5f
    val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

    Row(modifier = modifier) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(starSize)
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(starSize)
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Default.StarOutline,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(starSize)
            )
        }
    }
}


@Composable
fun LawyerItem(
    it: ILawyer,
    onItemClick: (Int) -> Unit,
    onRequestClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(it.id) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar
            AvatarImage(it.avatarURL)

            Spacer(modifier = Modifier.width(16.dp))

            // Info (ID, Name, Rating)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "ID: #${it.id}", fontSize = 12.sp, color = Colors.Blue117)

                Text(
                    text = it.fullName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
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
                        text = "${it.rating} (${it.reviewCount})",
                        fontSize = 14.sp
                    )

                    SpaceHorizontal(20.dp)

                    AppButton(
                        modifier = Modifier
                            .height(35.dp)
                            .wrapContentWidth(),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                        nameRes = R.string.all_request,
                        onClick = onRequestClick
                    )
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = Colors.Gray238
    )
}


@Composable
fun ReviewItem(it: IReview) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar
            AvatarImage(it.avatarURL.safe(), 48.dp)

            Spacer(modifier = Modifier.width(16.dp))

            // Info (Name, time ago)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(text = it.createAt, fontSize = 12.sp, color = Colors.Blue117)
            }
        }

        RatingStars(it.rating)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = it.contents,
            fontSize = 14.sp,
            color = Colors.Blue117
        )
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = Colors.Gray238
    )
}



