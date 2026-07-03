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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import kotlinx.coroutines.launch

@Composable
fun AddCategoryDialog(
    category: Category? = null,
    onConfirm: (String, Int, Category?) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(category?.name ?: "") }
    var selectedColor by remember { mutableIntStateOf(category?.color?: CategoryEntity.categoryColors.random().toArgb()) }
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(selectedColor)
        )
    }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {}) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = noteBackgroundAnimatable.value,
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
                                    color = if (colorInt == noteBackgroundAnimatable.value.toArgb()) {
                                        Color.Black
                                    } else Color.Transparent,
                                    CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        selectedColor = colorInt
                                        noteBackgroundAnimatable.animateTo(
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
                    maxLines = 1,
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
                    TextButton(onClick = { onConfirm(name, selectedColor, category) }) { Text("Add", color = Color.Black) }
                }
            }
        }
    }
}