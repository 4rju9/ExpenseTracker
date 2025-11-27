package app.netlify.dev4rju9.expensetracker.domain.model

data class Expense(
    val id: Long,
    val categoryId: Long,
    val title: String,
    val amount: Double,
    val timestamp: Long
)
