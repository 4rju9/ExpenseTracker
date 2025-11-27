package app.netlify.dev4rju9.expensetracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import app.netlify.dev4rju9.expensetracker.util.Utility.toDate

@Composable
fun CardItem (
    title: String,
    amount: Double,
    color: Int,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    curCornerSize: Dp = 30.dp,
    timestamp: Long = 0L,
    onClick: () -> Unit
) {

    Box (
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - curCornerSize.toPx(), 0f)
                lineTo(size.width, curCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(color, 0x000000, 0.3f)),
                    topLeft = Offset(size.width - curCornerSize.toPx(), -100f),
                    size = Size(curCornerSize.toPx() + 100f, curCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            )
        ) {
            Column (
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Expense: ₹$amount", style = MaterialTheme.typography.bodyMedium)
                    if (timestamp != 0L) {
                        Text(timestamp.toDate(), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}