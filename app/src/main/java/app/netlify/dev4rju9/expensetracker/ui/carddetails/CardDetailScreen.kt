package app.netlify.dev4rju9.expensetracker.ui.carddetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.ui.components.AddExpenseDialog
import app.netlify.dev4rju9.expensetracker.ui.components.CardItem
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(
    categoryId: Long,
    viewModel: CardDetailViewModel = koinViewModel()
) {
    val month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

    val expenses by viewModel.expenses(categoryId, month).collectAsState()
    val total by viewModel.total(categoryId, month).collectAsState()
    var category: Category? by remember { mutableStateOf(null) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        category = viewModel.get(categoryId)
    }

    Scaffold(
        floatingActionButton = {
            Row (
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: ₹$total",
                    style = MaterialTheme.typography.titleLarge
                )
                FloatingActionButton(
                    onClick = { showDialog = true },
                    shape = CircleShape
                ) { Icon(Icons.Default.Add, contentDescription = "Add Item") }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            val title = category?.name ?: ""

            Text(
                "$title Expenses",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(expenses) { expense ->
                    CardItem(
                        title = expense.title,
                        amount = expense.amount,
                        color = category?.color ?: CategoryEntity.categoryColors.random().toArgb(),
                        timestamp = expense.timestamp
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddExpenseDialog(
            color = category?.color ?: CategoryEntity.categoryColors.random().toArgb(),
            onConfirm = { title, amount ->
                viewModel.add(categoryId, title, amount)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}