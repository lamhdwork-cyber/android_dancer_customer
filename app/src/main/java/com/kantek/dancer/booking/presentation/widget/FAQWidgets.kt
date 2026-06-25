package com.kantek.dancer.booking.presentation.widget

import android.media.MediaPlayer
import android.support.ui.widget.AppButton
import android.support.ui.widget.AppInputText
import android.support.ui.widget.AppLazyColumn
import android.support.ui.widget.SpaceVertical
import android.support.ui.widget.rememberShimmerBrush
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.faqs.ILegalAnswer
import com.kantek.dancer.booking.domain.model.faqs.ILegalCategory
import com.kantek.dancer.booking.domain.model.faqs.ILegalQuestion
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun FQAsLoading() {
    val shimmerBrush = rememberShimmerBrush()

    Column(modifier = Modifier.padding(16.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(20.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )

        SpaceVertical(12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush)
        )
    }
}


@Composable
fun SubmitQuestionDialog(
    onDismiss: () -> Unit,
    onConfirm: (Pair<String, String>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text(stringResource(R.string.faqs_submit_your_question)) },
        text = {
            Column {

//                AppInputText(
//                    value = title,
//                    placeHolderRes = R.string.all_title,
//                    keyboardOptions = KeyboardOptions(
//                        capitalization = KeyboardCapitalization.Sentences,
//                        keyboardType = KeyboardType.Text
//                    ),
//                    onValueChange = { title = it }
//                )

                AppInputText(
                    value = question,
                    lightBackground = true,
                    singleLine = false,
                    maxLines = 6,
                    modifier = Modifier.height(150.dp),
                    placeHolderRes = R.string.faqs_your_question,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { question = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(title to question)
                },
                modifier = Modifier.widthIn(min = 80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Primary, contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 10.dp,
                    focusedElevation = 10.dp,
                    hoveredElevation = 10.dp
                )
            ) {
                Text(stringResource(R.string.all_submit))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.all_cancel), color = Colors.Gray146)
            }
        })
}


@Composable
fun SubmitAnswerDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var question by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text(stringResource(R.string.all_submit_your_answer)) },
        text = {
            AppInputText(
                value = question,
                lightBackground = true,
                singleLine = false,
                maxLines = 6,
                modifier = Modifier.height(150.dp),
                placeHolderRes = R.string.faqs_your_answer,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                ),
                onValueChange = { question = it }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(question)
                },
                modifier = Modifier.widthIn(min = 80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Primary, contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 10.dp,
                    focusedElevation = 10.dp,
                    hoveredElevation = 10.dp
                )
            ) {
                Text(stringResource(R.string.all_submit))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.all_cancel), color = Colors.Gray146)
            }
        })
}


@Composable
fun QuestionSentDialog(
    textDismiss: String = stringResource(R.string.all_close),
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        containerColor = Color.White,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                contentDescription = null,
                tint = Colors.Green103
            )
        },
        title = {
            Text(
                stringResource(R.string.all_sent),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                stringResource(R.string.msg_create_question_sent),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                onDismiss?.let {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.Blue241,
                            contentColor = Colors.Primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 1.dp,
                            pressedElevation = 10.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Text(
                            textDismiss,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQsAnswerBottomSheet(
    sheetState: SheetState,
    items: List<ILegalAnswer>,
    title: String = "Question content",
    isMoreLoading: Boolean,
    onDismiss: () -> Unit,
    onReply: () -> Unit,
    onLoadMore: (() -> Unit)? = null,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(),
        onDismissRequest = onDismiss
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    textAlign = TextAlign.Center,
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                SpaceVertical(20.dp)

                AppButton(
                    nameRes = R.string.all_submit_your_answer
                ) {
                    onReply()
                }

                SpaceVertical(30.dp)
                Box(modifier = Modifier.fillMaxHeight()) {
                    AppLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        items = items,
                        backgroundColor = Color.Transparent,
                        keyItem = { it.id },
                        isLoading = isMoreLoading,
                        onLoadMore = { onLoadMore?.invoke() }
                    ) { item, _, _ ->
                        FAQsAnswerItem(item)
                    }
                }
            }
        }
    }
}


@Composable
fun FAQThreadsCategoryItem(
    item: ILegalCategory,
    onClick: (ILegalCategory) -> Unit
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
            onClick(item)
            mediaPlayer.start()
        }
    ) {
        Column(
            modifier = Modifier
                .background(Colors.Gray238)
                .padding(vertical = 18.dp, horizontal = 14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Gray238),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Go",
                    tint = Color.Gray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}



@Composable
fun FAQThreadsQuestionItem(
    item: ILegalQuestion,
    onClick: (ILegalQuestion) -> Unit
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
            onClick(item)
            mediaPlayer.start()
        }
    ) {
        Column(
            modifier = Modifier
                .background(Colors.Gray238)
                .padding(vertical = 18.dp, horizontal = 14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Gray238),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Go",
                    tint = Color.Gray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}



@Composable
fun FAQsAnswerItem(it: ILegalAnswer) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarImage(it.avatarURL, 40.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = it.timeAgo,
                    color = Colors.Blue117
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = it.content,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}



