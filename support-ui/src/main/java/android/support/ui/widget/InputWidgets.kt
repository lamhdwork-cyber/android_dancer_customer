package android.support.ui.widget
import android.support.ui.extension.rememberDebouncedClick

import android.support.ui.R
import android.support.ui.formatter.USPhoneNumberTransformation
import android.support.ui.theme.CoreColors
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppInputPhoneNumber(
    value: String = "",
    readOnly: Boolean = false,
    @StringRes placeHolderRes: Int,
    modifier: Modifier = Modifier.fillMaxWidth(),
    /** When true, field uses light fill/border/text for white surfaces (same as [AppInputText]). */
    lightBackground: Boolean = true,
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit = {}
) {
    val placeHolder = stringResource(id = placeHolderRes)
    val hintPhone = stringResource(R.string.all_phone_hint_format)
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

    val borderColor = when {
        isFocused -> CoreColors.Primary
        lightBackground -> CoreColors.Gray238
        else -> CoreColors.Dark1E293B
    }
    val borderWidth = if (isFocused) 2.dp else 1.dp
    val fieldBackground = if (lightBackground) CoreColors.Gray249 else CoreColors.Dark660F172A
    val labelColor = if (lightBackground) CoreColors.Gray146 else CoreColors.GrayCBD5E1
    val textColor = if (lightBackground) Color.Black else CoreColors.GrayF1F5F9
    val hintColor = if (lightBackground) CoreColors.Gray146 else CoreColors.Dark475569
    val iconTint = if (isFocused) CoreColors.Primary else CoreColors.Gray6B7280

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = placeHolder,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
            color = labelColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val stripped = numericRegex.replace(newValue.text, "")
                val phoneNumber = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else {
                    stripped
                }
                textFieldValue = newValue.copy(
                    text = phoneNumber,
                    selection = if (newValue.text != phoneNumber) {
                        TextRange(phoneNumber.length)
                    } else {
                        newValue.selection
                    }
                )
                onValueChange(phoneNumber)
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
                .background(fieldBackground, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            readOnly = readOnly,
            textStyle = TextStyle(color = textColor, fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1,
            minLines = 1,
            visualTransformation = USPhoneNumberTransformation(),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(CoreColors.Primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(24.dp),
                            tint = iconTint
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = hintPhone,
                                color = hintColor,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdown(
    valueSelected: T?,
    @StringRes placeHolderRes: Int,
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
                focusedIndicatorColor = CoreColors.Primary,
                unfocusedIndicatorColor = CoreColors.Gray238,
                cursorColor = CoreColors.Primary
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
            modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = rememberDebouncedClick {
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
    @StringRes placeHolderRes: Int,
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
                focusedIndicatorColor = CoreColors.Primary,
                unfocusedIndicatorColor = CoreColors.Gray238,
                cursorColor = CoreColors.Primary
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
            modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
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
                                    tint = CoreColors.Primary
                                )
                            }
                        }
                    },
                    onClick = rememberDebouncedClick {
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
fun AppInputText(
    value: String = "",
    @StringRes placeHolderRes: Int,
    @StringRes hintRes: Int? = null,
    @DrawableRes leadingIconRes: Int? = null,
    /** When set, shown instead of [leadingIconRes] (Material / Compose icons, e.g. [Icons.Outlined.Email]). */
    leadingIcon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLength: Int = Int.MAX_VALUE,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    /** When true, field uses light fill/border/text for white surfaces. When false, matches guest sign-in (dark translucent field). */
    lightBackground: Boolean = false,
    onValueChange: (String) -> Unit = {}
) {
    val placeHolder = stringResource(id = placeHolderRes)
    val hintText = hintRes?.let { stringResource(id = it) }
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

    val iconTint = if (isFocused) CoreColors.Primary else CoreColors.Dark64748B
    val borderColor = when {
        isFocused -> CoreColors.Primary
        lightBackground -> CoreColors.Gray238
        else -> CoreColors.Dark1E293B
    }
    val borderWidth = if (isFocused) 2.dp else 1.dp
    val fieldBackground = if (lightBackground) CoreColors.Gray249 else CoreColors.Dark660F172A
    val labelColor = if (lightBackground) CoreColors.Gray146 else CoreColors.GrayCBD5E1
    val textColor = if (lightBackground) Color.Black else CoreColors.GrayF1F5F9
    val hintColor = if (lightBackground) CoreColors.Gray146 else CoreColors.Dark475569
    val effectiveMaxLines = if (singleLine) 1 else maxLines.coerceAtLeast(1)
    val resolvedLeadingIcon: ImageVector? =
        leadingIcon ?: leadingIconRes?.let { ImageVector.vectorResource(id = it) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = placeHolder,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
            color = labelColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        BasicTextField(
            value = textFieldValue,
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
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = if (singleLine) 56.dp else 48.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
                .background(fieldBackground, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            readOnly = readOnly,
            textStyle = TextStyle(color = textColor, fontSize = 16.sp),
            keyboardOptions = if (isPassword) {
                KeyboardOptions(keyboardType = KeyboardType.Text)
            } else {
                keyboardOptions
            },
            singleLine = singleLine,
            maxLines = effectiveMaxLines,
            minLines = 1,
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            interactionSource = interactionSource,
            cursorBrush = SolidColor(CoreColors.Primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (resolvedLeadingIcon != null) {
                        Icon(
                            imageVector = resolvedLeadingIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(24.dp),
                            tint = iconTint
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        if (hintText != null && textFieldValue.text.isEmpty()) {
                            Text(
                                text = hintText,
                                color = hintColor,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                    if (isPassword) {
                        IconButton(
                            onClick = rememberDebouncedClick { passwordVisible = !passwordVisible },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Outlined.Visibility
                                } else {
                                    Icons.Outlined.VisibilityOff
                                },
                                contentDescription = null,
                                tint = CoreColors.Gray6B7280
                            )
                        }
                    }
                }
            }
        )
    }
}
