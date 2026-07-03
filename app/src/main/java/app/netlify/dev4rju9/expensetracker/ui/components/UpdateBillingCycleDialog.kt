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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import app.netlify.dev4rju9.expensetracker.ui.dashboard.DashboardViewModel
import app.netlify.dev4rju9.expensetracker.util.getBillingCycleDay

@Composable
fun UpdateBillingCycleDialog(
    day: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    var billingCycleDay by remember { mutableStateOf(day.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.Black
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Manage Billing Cycle", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = billingCycleDay,
                    onValueChange = {
                        if (it.isDigitsOnly() && it.toInt() <= 31) {
                            billingCycleDay = it
                        }
                    },
                    placeholder = { Text("Day") },
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
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Black) }
                    TextButton(onClick = {
                        if (billingCycleDay.isNotEmpty() && billingCycleDay != day.toString()) {
                            onConfirm(billingCycleDay.toInt())
                        }
                    }) { Text("Add", color = Color.Black) }
                }
            }
        }
    }
}