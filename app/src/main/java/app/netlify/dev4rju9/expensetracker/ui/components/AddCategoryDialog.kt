package app.netlify.dev4rju9.expensetracker.ui.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import kotlinx.coroutines.launch

@Composable
fun AddCategoryDialog(
    onConfirm: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedColor by remember { mutableIntStateOf(CategoryEntity.categoryColors.random().toArgb()) }
    val noteBackgrounAnimatable = remember {
        Animatable(
            Color(selectedColor)
        )
    }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = onDismiss) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = noteBackgrounAnimatable.value,
                contentColor = Color.Black
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Add Category", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryEntity.categoryColors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .shadow(15.dp, CircleShape)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    3.dp,
                                    color = if (colorInt == noteBackgrounAnimatable.value.toArgb()) {
                                        Color.Black
                                    } else Color.Transparent,
                                    CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        selectedColor = colorInt
                                        noteBackgrounAnimatable.animateTo(
                                            targetValue = Color(colorInt),
                                            animationSpec = tween(500)
                                        )
                                    }
                                }
                        ) {

                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Category name") },
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Black
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Black) }
                    TextButton(onClick = { onConfirm(name, selectedColor) }) { Text("Add", color = Color.Black) }
                }
            }
        }
    }
}