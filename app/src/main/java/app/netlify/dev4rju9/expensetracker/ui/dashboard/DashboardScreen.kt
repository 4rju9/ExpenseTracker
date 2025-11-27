package app.netlify.dev4rju9.expensetracker.ui.dashboard

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.netlify.dev4rju9.expensetracker.ui.components.AddCategoryDialog
import app.netlify.dev4rju9.expensetracker.ui.components.CardItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    onNavigateToCategory: (Long) -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val searchResult by viewModel.search(searchQuery).collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    val isSearching = searchQuery.isNotBlank()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape
            ) { Icon(Icons.Default.Add, contentDescription = "Add Category") }
        }
    ) { padding ->
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
                Text(text = selectedMonth, style = MaterialTheme.typography.titleMedium)
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
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search Categories") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Close, contentDescription = "Clear") }
                    }
                }
            )

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
                        onClick = { onNavigateToCategory(category.id) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddCategoryDialog(
            onConfirm = { name, color ->
                viewModel.addNewCategory(name, color)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}