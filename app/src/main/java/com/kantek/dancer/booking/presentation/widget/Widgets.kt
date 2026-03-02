package com.kantek.dancer.booking.presentation.widget

import android.app.Activity
import android.media.MediaPlayer
import android.support.core.extensions.safe
import android.util.Log
import android.view.Gravity
import android.webkit.WebView
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.HorizontalOrVertical
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.domain.formatter.USPhoneNumberTransformation
import com.kantek.dancer.booking.domain.model.support.BottomNavigationScreen
import com.kantek.dancer.booking.domain.model.ui.Command
import com.kantek.dancer.booking.domain.model.ui.IImage
import com.kantek.dancer.booking.domain.model.ui.ILabel
import com.kantek.dancer.booking.domain.model.ui.booking.IBooking
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalAnswer
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalCategory
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalQuestion
import com.kantek.dancer.booking.domain.model.ui.review.IReview
import com.kantek.dancer.booking.domain.model.ui.search.ICity
import com.kantek.dancer.booking.domain.model.ui.search.ISpeciality
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.domain.model.ui.user.ILoginAgent
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import com.kantek.dancer.booking.presentation.extensions.loadUrlData
import com.kantek.dancer.booking.presentation.extensions.observe
import com.kantek.dancer.booking.presentation.theme.Colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Composable
fun ComponentView(item: Any, onCommand: (Command) -> Unit = {}) {
    return when (item) {
        is ILabel -> LabelView(item)
        is ILoginAgent -> AgentView(item, onCommand)
        is IImage -> AppImageView(item)
        else -> UnknownView(item)
    }
}

@Composable
fun UnknownView(item: Any) {
    val text = item.observe()
    return Text(text = text.toString())
}

@Composable
fun LabelView(item: ILabel) {
    val text = item.observe()
    return Text(text = text.toString())
}

@Composable
fun AppImageView(item: IImage) {
    val text = item.observe()
    return Text(text = text.toString())
}

@Composable
fun AgentView(it: ILoginAgent, onCommand: (Command) -> Unit = {}) {
    val agent = it.observe()
    Text(
        text = agent.name.toString(), Modifier.clickable(onClick = { onCommand(Command.Click(it)) })
    )
}

@Composable
fun ActionBarBackAndTitleView(
    textRes: Int = R.string.app_name,
    onCommand: (Command) -> Unit = {}
) {
    ActionBarBackAndTitleView(stringResource(textRes), onCommand)
}

@Composable
fun ActionBarBackAndTitleView(
    text: String = stringResource(R.string.app_name),
    onCommand: (Command) -> Unit = {}
) {
    Surface(
        color = Color.White, shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                colorFilter = ColorFilter.tint(Color.Black),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        indication = rememberRipple(bounded = true, radius = 24.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onCommand(Command.ActionBarBack) }
                    .padding(16.dp)
            )

            Text(
                text = text,
                color = Color.Black,
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
fun ActionBarMainView(textRes: Int = R.string.nav_home) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White, shadowElevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(56.dp)
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(textRes),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)

            )
        }
    }
}

@Composable
fun SpaceVertical(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpaceHorizontal(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun LoadingView(
    isShowing: Boolean,
    textLoadingRes: Int = R.string.all_loading,
    onDismissRequest: () -> Unit = {}
) {
    if (isShowing) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(
                        color = Colors.Primary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = stringResource(id = textLoadingRes),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun AppInputText(
    value: String = "",
    @StringRes placeHolderRes: Int = R.string.app_name,
    @DrawableRes leadingIconRes: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLength: Int = Int.MAX_VALUE,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit = {}
) {
    val placeHolder = stringResource(id = placeHolderRes)
    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            textFieldValue = textFieldValue.copy(selection = TextRange(value.length))
        }
    }

    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(text = value, selection = TextRange(value.length))
        }
    }

    OutlinedTextField(
        singleLine = singleLine,
        value = textFieldValue,
        maxLines = maxLines,
        readOnly = readOnly,
        onValueChange = { newValue ->
            val filteredText = newValue.text.take(maxLength)
            textFieldValue = newValue.copy(
                text = filteredText,
                selection = if (newValue.text != filteredText) {
                    TextRange(filteredText.length)
                } else {
                    newValue.selection
                }
            )
            onValueChange(filteredText)
        },
        label = { Text(placeHolder, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(fontSize = if (isFocused) 16.sp else 14.sp),
        interactionSource = interactionSource,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Colors.Primary,
            unfocusedIndicatorColor = Colors.Gray238,
            cursorColor = Colors.Primary
        ),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = leadingIconRes?.let {
            {
                Icon(
                    imageVector = ImageVector.vectorResource(id = leadingIconRes),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Number) else keyboardOptions)
}

@Composable
fun AppInputPhoneNumber(
    value: String = "",
    readOnly: Boolean = false,
    @StringRes placeHolderRes: Int = R.string.app_name,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: (String) -> Unit = {}
) {
    val placeHolder = stringResource(id = placeHolderRes)
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val numericRegex = Regex("[^0-9]")

    LaunchedEffect(isFocused) {
        if (isFocused) {
            textFieldValue = textFieldValue.copy(selection = TextRange(value.length))
        }
    }

    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(text = value, selection = TextRange(value.length))
        }
    }

    OutlinedTextField(
        value = textFieldValue,
        readOnly = readOnly,
        label = { Text(placeHolder, fontSize = 14.sp) },
        onValueChange = {
            val stripped = numericRegex.replace(it.text, "")
            val phoneNumber = if (stripped.length >= 10) {
                stripped.substring(0..9)
            } else {
                stripped
            }
            textFieldValue = it.copy(
                text = phoneNumber,
                selection = if (it.text != phoneNumber) {
                    TextRange(phoneNumber.length)
                } else {
                    it.selection
                }
            )
            onValueChange(phoneNumber)
        },
        textStyle = TextStyle(fontSize = if (isFocused) 16.sp else 14.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Colors.Primary,
            unfocusedIndicatorColor = Colors.Gray238,
            cursorColor = Colors.Primary
        ),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        visualTransformation = USPhoneNumberTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdown(
    valueSelected: T?,
    @StringRes placeHolderRes: Int = R.string.app_name,
    @DrawableRes trailingIconRes: Int = R.drawable.ic_dropdown,
    items: List<T>,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueSelected: (T) -> Unit
) {
    val placeHolder = stringResource(id = placeHolderRes)
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "DropdownIconRotation"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = valueSelected?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 14.sp),
            label = { Text(placeHolder, fontSize = 14.sp) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Colors.Primary,
                unfocusedIndicatorColor = Colors.Gray238,
                cursorColor = Colors.Primary
            ),
            shape = RoundedCornerShape(12.dp),
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = trailingIconRes),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .rotate(rotation)
                        .size(20.dp),
                )
            },
            modifier = modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = {
                        onValueSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppMultiSelectDropdown(
    valueSelected: List<T>?,
    @StringRes placeHolderRes: Int = R.string.app_name,
    @DrawableRes trailingIconRes: Int = R.drawable.ic_dropdown,
    items: List<T>,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueSelected: (List<T>?) -> Unit
) {
    val placeHolder = stringResource(id = placeHolderRes)
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "DropdownIconRotation"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = if (valueSelected.isNullOrEmpty()) "" else valueSelected.joinToString(),
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 14.sp),
            label = { Text(placeHolder, fontSize = 14.sp) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Colors.Primary,
                unfocusedIndicatorColor = Colors.Gray238,
                cursorColor = Colors.Primary
            ),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = trailingIconRes),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .rotate(rotation)
                        .size(20.dp),
                )
            },
            modifier = modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { option ->
                val isSelected = valueSelected?.contains(option) == true
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(option.toString())
                            if (isSelected) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Colors.Primary
                                )
                            }
                        }
                    },
                    onClick = {
                        val newSelection = valueSelected?.toMutableList()
                        if (isSelected) {
                            newSelection?.remove(option)
                        } else {
                            newSelection?.add(option)
                        }
                        onValueSelected(newSelection)
                    }
                )
            }
        }
    }
}

@Composable
fun AppNextButton(
    @StringRes nameRes: Int,
    @ColorInt backgroundColor: Color = Colors.Primary,
    @ColorInt textColor: Color = Color.White,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.height(55.dp),
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Button(
        onClick = {   // Play sound
            val mediaPlayer = MediaPlayer.create(context, R.raw.sound_button)
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
            onClick()
            mediaPlayer.start()
        },
        enabled = isEnabled,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 14.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 14.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = stringResource(id = nameRes), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        SpaceHorizontal(20.dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_button_right),
            contentDescription = "Icon",
            tint = Color.White
        )
    }

}

@Composable
fun AppButton(
    @StringRes nameRes: Int,
    @ColorInt backgroundColor: Color = Colors.Primary,
    @ColorInt textColor: Color = Color.White,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
        .height(55.dp)
        .fillMaxWidth(),
    contentPadding: PaddingValues = PaddingValues(start = 50.dp, end = 50.dp),
    fontSize: Int = 16,
    iconStartRes: Int = 0,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Button(
        onClick = {
            // Play sound
            val mediaPlayer = MediaPlayer.create(context, R.raw.sound_button)
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
            onClick()
            mediaPlayer.start()
        },
        enabled = isEnabled,
        contentPadding = contentPadding,
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 14.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 14.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (iconStartRes != 0) {
            Icon(
                painter = painterResource(id = iconStartRes),
                contentDescription = "Icon",
                tint = Colors.Primary
            )
            SpaceHorizontal(20.dp)
        }

        Text(text = stringResource(id = nameRes), fontSize = fontSize.sp)
    }
}

@Composable
fun AppDialog(
    title: String,
    message: String,
    textConfirm: String = stringResource(R.string.all_confirm),
    textDismiss: String = stringResource(R.string.all_cancel),
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            onConfirm?.let {
                Button(
                    onClick = onConfirm,
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
                    Text(textConfirm)
                }
            }
        },
        dismissButton = {
            onDismiss?.let {
                TextButton(onClick = it) {
                    Text(textDismiss, color = Colors.Gray146)
                }
            }
        })
}


@Composable
fun AppNotificationDialog(
    message: String, hasKillApp: Boolean = false, onDismiss: (Boolean) -> Unit
) {
    AppDialog(
        title = stringResource(R.string.all_notification),
        message = message,
        textConfirm = stringResource(R.string.all_ok),
        onConfirm = { onDismiss(hasKillApp) })
}

@Composable
fun AppConfirmDialog(
    title: String = stringResource(R.string.all_notification),
    message: String,
    textConfirm: String,
    onConfirm: (Boolean) -> Unit,
    hasKillApp: Boolean = false,
    onDismiss: (() -> Unit)? = null
) {
    AppDialog(
        title = title,
        message = message,
        textConfirm = textConfirm,
        onDismiss = onDismiss,
        onConfirm = { onConfirm(hasKillApp) })
}

@Composable
fun LogoutDialog(
    onLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    AppDialog(
        title = stringResource(R.string.all_logout),
        message = stringResource(R.string.message_logout_app),
        textConfirm = stringResource(R.string.all_logout),
        onDismiss = onDismiss,
        onConfirm = onLogout
    )
}

@Composable
fun AppBottomBar(
    selectedItemRouter: String,
    onItemRouterSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavigationScreen.Home,
        BottomNavigationScreen.Search,
        BottomNavigationScreen.Cases,
        BottomNavigationScreen.Notification,
        BottomNavigationScreen.Account
    )

    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = Color.Transparent, shadowElevation = 4.dp
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = Colors.Gray238
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach {
                val isSelected = (it.route == selectedItemRouter)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = { onItemRouterSelected(it.route) })
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .width(48.dp)
                            .background(
                                color = if (isSelected) Colors.Primary else Color.Transparent,
                                shape = RoundedCornerShape(18.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                                .height(5.dp)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(it.icon),
                            contentDescription = stringResource(it.titleRes),
                            tint = if (isSelected) Color.White else Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                                .height(5.dp)
                        )
                    }
                    val dynamicFontSize = bottomBarFontSize()
                    Text(
                        text = stringResource(it.titleRes),
                        color = if (isSelected) Colors.Primary else Color.Black,
                        fontSize = dynamicFontSize,
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

@Composable
fun SetSystemBarsColor(
    statusBarColor: Color = Color.White,
    navigationBarColor: Color = Color.Black,
    statusBarDarkIcons: Boolean = true,
    navigationBarDarkIcons: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor, darkIcons = statusBarDarkIcons)
        systemUiController.setNavigationBarColor(
            navigationBarColor,
            darkIcons = navigationBarDarkIcons
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppLazyColumn(
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: HorizontalOrVertical = Arrangement.spacedBy(0.dp),
    keyItem: ((T) -> Any)? = null,
    isLoading: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    backgroundColor: Color = Colors.Gray249,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    itemContent: @Composable (T, Int, Boolean) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.map { it.lastOrNull()?.index }
            .distinctUntilChanged().collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= items.lastIndex && !isLoading) {
                    onLoadMore?.invoke()
                }
            }
    }

    val pullToRefreshState = rememberPullToRefreshState()

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Box(
            modifier = if (onRefresh != null) Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
            else Modifier
        ) {
            LazyColumn(
                state = listState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement
            ) {
                itemsIndexed(
                    items,
                    key = { _, item -> keyItem?.invoke(item) ?: item.hashCode() }) { index, item ->
                    itemContent(item, index, index == items.lastIndex)
                }

                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Colors.Primary)
                        }
                    }
                }
            }

            LaunchedEffect(pullToRefreshState.isRefreshing) {
                if (pullToRefreshState.isRefreshing) {
                    onRefresh?.invoke()
                }
            }

            LaunchedEffect(isRefreshing) {
                if (isRefreshing && !pullToRefreshState.isRefreshing) {
                    pullToRefreshState.startRefresh()
                }
            }

            LaunchedEffect(pullToRefreshState.isRefreshing, isRefreshing) {
                if (pullToRefreshState.isRefreshing && !isRefreshing) {
                    pullToRefreshState.endRefresh()
                }
            }

            if (onRefresh != null)
                PullToRefreshContainer(
                    state = pullToRefreshState,
                    contentColor = Colors.Primary,
                    containerColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                )
        }
    }
}

@Composable
fun RotatingLoadingIndicator() {
    val rotation by animateFloatAsState(
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .graphicsLayer { rotationZ = rotation },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 4.dp, color = Colors.Primary)
    }
}

@Composable
fun SocialLoginButton(iconRes: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Social Icon",
            modifier = Modifier.size(24.dp)
        )
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
fun AvatarImage(
    url: String?, size: Dp = 70.dp,
    onClick: (() -> Unit)? = null
) {
    val shimmerBrush = rememberShimmerBrush(1000)

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url ?: "")
            .placeholder(R.drawable.img_logo_app)
            .error(R.drawable.img_logo_app)
            .crossfade(true)
            .allowHardware(false)
            .build()
    )

    val state = painter.state

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .border(2.dp, Colors.Blue227, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Avatar Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (state is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(shimmerBrush)
            )
        }
    }
}

@Composable
fun AppImage(
    url: String?,
    placeholderRes: Int = R.drawable.img_logo_app,
    errorRes: Int = R.drawable.img_logo_app,
    modifier: Modifier = Modifier,
    isShowLoading: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url ?: "")
            .placeholder(placeholderRes)
            .error(errorRes)
            .crossfade(true)
            .allowHardware(false)
            .build()
    )

    val state = painter.state

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = "Image",
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
        if (state is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                if (isShowLoading)
                    CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

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
fun NoDataView(@StringRes htmlRes: Int) {
    val context = LocalContext.current
    val htmlString = remember { context.getString(htmlRes) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = {
                TextView(it).apply {
                    text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    textSize = 14f
                    gravity = Gravity.CENTER
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPhotoPickerDialog(
    sheetState: SheetState,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.choose_avatar),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                                onCameraClick()
                            }
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Camera",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                    text = stringResource(R.string.photo_from_camera),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                                onGalleryClick()
                            }
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Filled.PhotoLibrary,
                    contentDescription = "Gallery",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                    text = stringResource(R.string.photo_from_gallery),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun HomeBannerLoading() {
    val shimmerBrush = rememberShimmerBrush()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.2f), Color.Transparent)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(shimmerBrush)
            )

            SpaceVertical(12.dp)

            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(shimmerBrush)
            )

            SpaceVertical(20.dp)

            Box(
                modifier = Modifier
                    .height(55.dp)
                    .width(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(shimmerBrush)
            )
        }
    }
}

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
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                setTextColor(Color.Black.toArgb())
                textSize = 14f
            }
        }
    )
}

@Composable
fun HtmlStyledText(
    html: String,
    modifier: Modifier
) {
    val spanned = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    Text(
        text = buildAnnotatedString {
            append(spanned.toString())
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPlaceAutocomplete(
    value: String,
    @StringRes placeHolderRes: Int = R.string.app_name,
    @DrawableRes trailingIconRes: Int? = null,
    modifier: Modifier? = null,
    onPlaceSelected: (Place) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val placeHolder = stringResource(id = placeHolderRes)
    var expanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                result.data?.let { intent ->
                    val place = Autocomplete.getPlaceFromIntent(intent)
                    onPlaceSelected(place)
                }
            }

            AutocompleteActivity.RESULT_ERROR -> {
                val status = Autocomplete.getStatusFromIntent(result.data!!)
                Log.e("AppPlaceAutocomplete", "Autocomplete error: ${status.statusMessage}")
            }

            Activity.RESULT_CANCELED -> {
                Log.d("AppPlaceAutocomplete", "User canceled or intent failed")
            }
        }
    }

    var launchError by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(launchError) {
        launchError?.let { code ->
            activity?.let {
                GoogleApiAvailability.getInstance()
                    .showErrorDialogFragment(it, code, 30422)
            }
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = false
            try {
                val fields = listOf(
                    Place.Field.NAME,
                    Place.Field.ADDRESS
//                    Place.Field.LAT_LNG,
//                    Place.Field.ADDRESS_COMPONENTS
                )
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setInitialQuery(value)
                    .build(context)
                launcher.launch(intent)
            } catch (e: GooglePlayServicesRepairableException) {
                launchError = e.connectionStatusCode
            } catch (e: GooglePlayServicesNotAvailableException) {
                launchError = e.errorCode
            }
        },
        modifier = modifier ?: Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 14.sp),
            label = { Text(placeHolder, fontSize = 14.sp) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Colors.Primary,
                unfocusedIndicatorColor = Colors.Gray238,
                cursorColor = Colors.Primary
            ),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                trailingIconRes?.let {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = trailingIconRes),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
    }
}

@Composable
fun BookingSuccessDialog(
    title: String,
    message: String,
    textConfirm: String = stringResource(R.string.all_view_detail),
    textDismiss: String = stringResource(R.string.all_close),
    onConfirm: (() -> Unit)? = null,
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
                title,
                textAlign = TextAlign.Center
            )
        },
        text = { Text(message, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
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
                onConfirm?.let {
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.Primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 10.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Text(
                            textConfirm,
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

@Composable
fun CaseItemView(
    it: IBooking,
    onItemClick: () -> Unit,
    onRequestClick: () -> Unit,
    onLawyerClick: () -> Unit,
    onCancelClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Case ID and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.case_id_s, it.id), fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(it.colorStatus)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = it.statusDisplay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            SpaceVertical(10.dp)

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Colors.Gray238
            )

            SpaceVertical(14.dp)

            // Description Title
            Text(
                stringResource(R.string.find_issue_des),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.Blue247, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = it.description, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location & Time
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_datetime),
                    contentDescription = null,
                    tint = Colors.Blue66,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = it.datetime, color = Colors.Blue95, fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_address),
                    contentDescription = null,
                    tint = Colors.Blue66,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = it.address, color = Colors.Blue95, fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (it.hasComplete && it.lawyer != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .background(Colors.Blue241, RoundedCornerShape(8.dp))
                        .clickable { onLawyerClick() }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    AvatarImage(url = it.lawyer?.avatarURL, size = 40.dp)

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = it.lawyer?.fullName.safe(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowRight,
                        contentDescription = "Navigate",
                        tint = Color.Black
                    )
                }
            }

            if (it.hasCancel) {
                if (it.reason.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Reason
                    Text(
                        stringResource(R.string.all_cancel_reason),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = it.reason, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            AppButton(
                R.string.all_chat,
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                textColor = Colors.Primary,
                backgroundColor = Colors.Blue227,
                iconStartRes = R.drawable.ic_ab_chat,
                fontSize = 14,
            ) { onChatClick() }

            if (it.hasCancel) {
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    nameRes = R.string.all_request_again,
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth(),
                    fontSize = 14,
                    onClick = onRequestClick
                )
            }

            // Cancel Button
            if (it.hasShowButtonCancel) {
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    nameRes = R.string.all_cancel_request,
                    backgroundColor = Colors.Red247,
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth(),
                    fontSize = 14,
                    onClick = onCancelClick
                )
            }
        }
    }
}

@Composable
fun CancellationReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var reason by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text(stringResource(R.string.msg_cancel_reason)) },
        text = {
            Column {
                AppInputText(
                    value = reason,
                    singleLine = false,
                    maxLines = 6,
                    modifier = Modifier.height(150.dp),
                    placeHolderRes = R.string.all_cancellation_reason,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { reason = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(reason) },
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
                Text(stringResource(R.string.all_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.all_cancel), color = Colors.Gray146)
            }
        })
}


@Composable
fun BoxText(
    text: String,
    textColor: Color = Colors.Primary,
    boxColor: Color = Colors.Blue241
) {
    Box(
        modifier = Modifier
            .background(
                color = boxColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

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
fun CallPhoneDialog(
    phoneNumber: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AppConfirmDialog(
        message = stringResource(R.string.msg_confirm_call_phone_s, phoneNumber),
        textConfirm = stringResource(R.string.all_call),
        onConfirm = {
            onConfirm()
        }, onDismiss = {
            onDismiss()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomablePager(imageUrls: List<String>) {
    val pagerState = rememberPagerState { imageUrls.size }
    var currentScale by remember { mutableStateOf(1f) }

    HorizontalPager(
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(pagerState),
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = currentScale <= 1f
    ) { page ->
        ZoomableAsyncImage(
            imageUrl = imageUrls[page],
            onScaleChanged = { newScale ->
                currentScale = newScale
            }
        )
    }
}

@Composable
fun ZoomableAsyncImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onScaleChanged: ((Float) -> Unit)? = null
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val minScale = 1f
    val maxScale = 5f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                awaitEachGesture {
                    do {
                        val event = awaitPointerEvent()
                        val zoomChange = event.calculateZoom()
                        val panChange = event.calculatePan()

                        val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)
                        onScaleChanged?.invoke(newScale)

                        if (newScale > 1f) {
                            val maxOffset = 1000f
                            offset = Offset(
                                x = (offset.x + panChange.x).coerceIn(-maxOffset, maxOffset),
                                y = (offset.y + panChange.y).coerceIn(-maxOffset, maxOffset)
                            )
                        } else {
                            offset = Offset.Zero
                        }

                        scale = newScale
                    } while (event.changes.any { it.pressed })

                    if (scale <= 1f) {
                        onScaleChanged?.invoke(1f)
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > minScale) {
                            offset = Offset.Zero
                            1f
                        } else {
                            2.5f
                        }
                        onScaleChanged?.invoke(scale)
                    }
                )
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
    ) {
        AppImage(
            url = imageUrl,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun AppWebView(
    url: String,
    modifier: Modifier = Modifier,
    onLoading: (Boolean) -> Unit = {},
    onError: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val webView = remember { WebView(context) }

    DisposableEffect(Unit) {
        onDispose {
            webView.stopLoading()
            webView.destroy()
        }
    }

    AndroidView(
        factory = {
            webView.loadUrlData(
                owner = lifecycleOwner,
                url = url,
                onLoading = onLoading,
                onError = onError
            )
            webView
        },
        modifier = modifier
    )
}

@Composable
fun OtpVerificationScreen(
    otpLength: Int = 6,
    onOtpComplete: (String) -> Unit,
    onResend: () -> Unit,
    isLoading: Boolean = false,
    isOtpInvalid: Boolean = false,
    errorMessage: String? = null
) {
    val otpValues = remember { List(otpLength) { mutableStateOf("") } }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    var timer by remember { mutableStateOf(AppConfig.OTP_TIME_OUT) }
    var isResendVisible by remember { mutableStateOf(false) }
    var shouldFocus by remember { mutableStateOf(false) }

    // Countdown timer
    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        } else {
            isResendVisible = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value.value,
                    onValueChange = {
                        if (it.length <= 1) {
                            value.value = it
                            if (it.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else if (it.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    },
                    isError = isOtpInvalid,
                    modifier = Modifier
                        .width(45.dp)
                        .focusRequester(focusRequesters[index])
                        .focusProperties {
                            if (index < otpLength - 1) {
                                next = focusRequesters[index + 1]
                            }
                            if (index > 0) {
                                previous = focusRequesters[index - 1]
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }
        }

        // Display error
        if (isOtpInvalid && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        val isOtpComplete = otpValues.all { it.value.isNotEmpty() }

        Button(
            onClick = {
                val otp = otpValues.joinToString("") { it.value }
                onOtpComplete(otp)
            },
            enabled = isOtpComplete && !isLoading,
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Colors.Primary,
                        strokeWidth = 3.dp,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 10.dp, top = 5.dp),
                    )
                }
                Text(
                    stringResource(R.string.all_contiue),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isResendVisible) {
            TextButton(onClick = {
                otpValues.forEach { it.value = "" }
                timer = 60
                isResendVisible = false
                shouldFocus = true
                onResend()
            }) {
                Text(stringResource(R.string.auth_otp_resend))
            }
        } else {
            Text(stringResource(R.string.auth_otp_resend_in_remaining_s, timer))
        }
    }
    //Focus first box
    LaunchedEffect(Unit) {
        delay(300)
        focusRequesters.first().requestFocus()
    }

    LaunchedEffect(shouldFocus) {
        if (shouldFocus) {
            delay(50)
            focusRequesters.first().requestFocus()
            shouldFocus = false
        }
    }
}

@Composable
fun LegalDisclaimerDialog(
    hasAgree: Boolean = false,
    onAgree: () -> Unit,
    onDismiss: () -> Unit
) {
    var isChecked by remember { mutableStateOf(hasAgree) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.reg_legal_disclaimer),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { onDismiss() }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.reg_legal_disclaimer_content),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it }
                    )
                    Text(stringResource(R.string.reg_legal_disclaimer_confirm))
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAgree() },
                enabled = isChecked
            ) {
                Text(stringResource(R.string.reg_legal_disclaimer_agree))
            }
        }
    )
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
                imageVector = Icons.Default.StarHalf,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownItemWithCheck(
    label: String,
    options: List<String>,
    selected: String?,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField hiển thị giá trị
        TextField(
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },

                    // Dấu ✓ nằm bên phải
                    trailingIcon = {
                        if (option == selected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApply: () -> Unit,
    onReset: () -> Unit,
    hasShowCity: Boolean = false,
    selectedCity: ICity?,
    cities: List<ICity>,
    onSelectCity: (ICity) -> Unit,
    selectedLanguage: ILanguage?,
    languages: List<ILanguage>,
    onSelectLanguage: (ILanguage) -> Unit,
    selectedSpeciality: List<ISpeciality>?,
    specialities: List<ISpeciality>,
    onSelectSpeciality: (List<ISpeciality>?) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.all_filter),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            if (hasShowCity)
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppDropdown(
                        valueSelected = selectedCity,
                        placeHolderRes = R.string.filter_select_city,
                        trailingIconRes = R.drawable.ic_arrow_drop_down,
                        items = cities,
                    ) { onSelectCity(it) }

                }

            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppDropdown(
                    valueSelected = selectedLanguage,
                    placeHolderRes = R.string.filter_select_language,
                    trailingIconRes = R.drawable.ic_arrow_drop_down,
                    items = languages,
                ) { onSelectLanguage(it) }

            }

            Row(
                modifier = Modifier
                    .padding(start = 14.dp, end = 14.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppMultiSelectDropdown(
                    valueSelected = selectedSpeciality,
                    placeHolderRes = R.string.filter_select_specialities,
                    trailingIconRes = R.drawable.ic_arrow_drop_down,
                    items = specialities,
                ) { onSelectSpeciality(it) }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppButton(
                    modifier = Modifier.weight(1f),
                    nameRes = R.string.all_reset,
                    backgroundColor = Colors.Blue241,
                    textColor = Colors.Primary
                ) {
                    onReset()
                }
                SpaceHorizontal(12.dp)

                AppButton(nameRes = R.string.all_apply, modifier = Modifier.weight(1f)) {
                    onApply()
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 40.dp,
    spacing: Dp = 4.dp
) {
    Row(modifier = modifier) {
        for (i in 1..stars) {
            val icon = when {
                i <= rating -> Icons.Filled.Star
                i - rating in 0.5f..0.99f -> Icons.Filled.StarHalf
                else -> Icons.Filled.StarBorder
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier
                    .size(starSize)
                    .padding(end = spacing)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val half = starSize.toPx() / 2
                            val newRating = if (offset.x < half) i - 0.5f else i.toFloat()
                            onRatingChanged(newRating)
                        }
                    }
            )
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


