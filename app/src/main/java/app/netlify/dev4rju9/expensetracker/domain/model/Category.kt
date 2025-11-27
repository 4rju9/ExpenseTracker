package app.netlify.dev4rju9.expensetracker.domain.model

data class Category(
    val id: Long,
    val name: String,
    val total: Double,
    val color: Int,
    val createdMonth: String
)
