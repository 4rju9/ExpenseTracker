package app.netlify.dev4rju9.expensetracker.util

import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utility {

    fun CategoryEntity.toDomain(): Category {
        return Category(
            id = id,
            name = name,
            total = total,
            color = color,
            createdMonth = createdMonth
        )
    }

    fun ExpenseEntity.toDomain(): Expense {
        return Expense(
            id = id,
            categoryId = categoryId,
            title = title,
            amount = amount,
            timestamp = timestamp
        )
    }

    fun Long.toDate() : String {
        val date = Date(this)
        return SimpleDateFormat("MMM, dd", Locale.getDefault()).format(date)
    }

}