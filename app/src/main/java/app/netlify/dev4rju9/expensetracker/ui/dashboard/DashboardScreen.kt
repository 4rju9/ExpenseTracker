package app.netlify.dev4rju9.expensetracker.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.netlify.dev4rju9.expensetracker.ui.components.AddCategoryDialog
import app.netlify.dev4rju9.expensetracker.ui.components.CardItem
import app.netlify.dev4rju9.expensetracker.ui.components.UpdateBillingCycleDialog
import app.netlify.dev4rju9.expensetracker.util.saveBillingCycleDay
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    onNavigateToCategory: (Long, String) -> Unit, viewModel: DashboardViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val billingCycleDay by viewModel.billingDay.collectAsStateWithLifecycle()

    val selectedMonth by viewModel.selectedMonth.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    val showDialog by viewModel.showDialog.collectAsStateWithLifecycle()
    val showUpdateCycleDialog by viewModel.showBillingCycleDialog.collectAsStateWithLifecycle()
    val isSearching = searchQuery.isNotBlank()

    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val total = ((if (isSearching) searchResult else categories).sumOf { it.total })
                Text(
                    text = "Total: ₹%.2f".format(total), style = MaterialTheme.typography.titleLarge
                )
                FloatingActionButton(
                    onClick = { viewModel.toggleDialogState(true) }, shape = CircleShape
                ) { Icon(Icons.Default.Add, contentDescription = "Add Category") }
            }
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = viewModel::previousMonth) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Month"
                    )
                }
                Text(
                    text = selectedMonth,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        viewModel.toggleBillingDialogState(true)
                    })
                IconButton(onClick = viewModel::nextMonth, enabled = viewModel.hasNextMonth()) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Month"
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("Search Categories") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(
                                Icons.Default.Close, contentDescription = "Clear"
                            )
                        }
                    }
                })

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 items per row
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(if (isSearching) searchResult else categories) { category ->
                    CardItem(
                        title = category.name,
                        amount = category.total,
                        color = category.color,
                        modifier = Modifier,
                        cornerRadius = 10.dp,
                        curCornerSize = 30.dp,
                        onClick = { onNavigateToCategory(category.id, selectedMonth) },
                        onLongClick = {
                            viewModel.onCategorySelected(category)
                            viewModel.toggleDialogState(true)
                        })
                }
            }
        }
    }

    if (showDialog) {
        AddCategoryDialog(category = selectedCategory, onConfirm = { name, color, category ->
            viewModel.addNewCategory(name, color, category)
            viewModel.onCategorySelected(null)
            viewModel.toggleDialogState(false)
        }, onDismiss = {
            viewModel.onCategorySelected(null)
            viewModel.toggleDialogState(false)
        })
    }

    if (showUpdateCycleDialog) {
        UpdateBillingCycleDialog(day = billingCycleDay, onConfirm = {
            viewModel.toggleBillingDialogState(false)
            context.saveBillingCycleDay(it)
            viewModel.saveBillingDay(it)
        }, onDismiss = { viewModel.toggleBillingDialogState(false) })
    }
}