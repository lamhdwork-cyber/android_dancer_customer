package com.kantek.dancer.booking.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.presentation.theme.Colors

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



fun Modifier.sheetTopSideBorder(
    color: androidx.compose.ui.graphics.Color,
    strokeWidth: Float = 1f,
    cornerRadiusDp: Float = 32f,
): Modifier = this.then(
    Modifier.drawBehind {
        val sw = strokeWidth * density
        val r = cornerRadiusDp * density
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(0f, h)
            lineTo(0f, r)
            arcTo(
                rect = Rect(0f, 0f, 2 * r, 2 * r),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(w - r, 0f)
            arcTo(
                rect = Rect(w - 2 * r, 0f, w, 2 * r),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(w, h)
        }
        drawPath(path = path, color = color, style = Stroke(width = sw))
    }
)
