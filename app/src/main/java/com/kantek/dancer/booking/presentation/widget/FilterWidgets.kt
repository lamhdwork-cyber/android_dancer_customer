package com.kantek.dancer.booking.presentation.widget

import android.support.ui.widget.AppButton
import android.support.ui.widget.AppDropdown
import android.support.ui.widget.AppMultiSelectDropdown
import android.support.ui.widget.SpaceHorizontal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.search.ICity
import com.kantek.dancer.booking.domain.model.search.ISpeciality
import com.kantek.dancer.booking.domain.model.user.ILanguage
import com.kantek.dancer.booking.presentation.theme.Colors

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
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
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


