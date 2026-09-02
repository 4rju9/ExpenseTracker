package app.netlify.dev4rju9.expensetracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import kotlin.math.exp

@Composable
fun AddExpenseDialog(
    color: Int,
    expense: Expense? = null,
    onConfirm: (String, Double, Expense?) -> Unit,
    onRepeat: (String, Double) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(expense?.title ?: "") }
    var amount by remember {
        mutableStateOf(
            expense?.amount?.let { "%.2f".format(it) } ?: ""
        )
    }

    Dialog(onDismissRequest = {}) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = Color(color),
                contentColor = Color.Black
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Add Expense", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { if (!it.contains("\n")) title = it },
                    placeholder = { Text("Title") },
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

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { value ->
                        if (value.matches(Regex("-?\\d*(\\.\\d*)?"))) {
                            amount = value
                        }
                    },
                    placeholder = { Text("Amount") },
                    maxLines = 1,
                    shape = RoundedCornerShape(30.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
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
                    val double = amount.toDoubleOrNull() ?: 0.0
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Black) }
                    expense?.let {
                        TextButton(onClick = {
                            onRepeat(title, double)
                        }) { Text("Repeat", color = Color.Black) }
                    }
                    TextButton(onClick = {
                        onConfirm(title, double, expense)
                    }) { Text("Add", color = Color.Black) }
                }
            }
        }
    }
}