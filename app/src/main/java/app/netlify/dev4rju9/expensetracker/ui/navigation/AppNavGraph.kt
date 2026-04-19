package app.netlify.dev4rju9.expensetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.netlify.dev4rju9.expensetracker.ui.dashboard.DashboardScreen
import app.netlify.dev4rju9.expensetracker.ui.carddetails.CardDetailScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToCategory = { id, month ->
                    navController.navigate("cardDetail/$id/$month")
                }
            )
        }

        composable(
            "cardDetail/{categoryId}/{month}",
            arguments = listOf(
                navArgument("categoryId") { type = NavType.LongType },
                navArgument("month") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
            val currentMonth = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM"))
            val month = backStackEntry.arguments?.getString("month") ?: currentMonth
            CardDetailScreen(categoryId = categoryId, month = month)
        }
    }
}