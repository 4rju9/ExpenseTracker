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
                onNavigateToCategory = { id ->
                    navController.navigate("cardDetail/$id")
                }
            )
        }

        composable(
            "cardDetail/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
            CardDetailScreen(categoryId = categoryId)
        }
    }
}