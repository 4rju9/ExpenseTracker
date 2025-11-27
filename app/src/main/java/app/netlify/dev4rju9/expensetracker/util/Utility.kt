package app.netlify.dev4rju9.expensetracker.util

import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.model.Expense

object Utility {

    fun CategoryEntity.toDomain(): Category {
        return Category(
            id = id,
            name = name,
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

}