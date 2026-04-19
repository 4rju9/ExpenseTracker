package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository

class UpdateExpenseUseCase(private val repo: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) =
        repo.updateExpense(expense)
}