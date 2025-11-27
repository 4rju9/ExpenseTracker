package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository

class AddExpenseUseCase(private val repo: ExpenseRepository) {
    suspend operator fun invoke(categoryId: Long, title: String, amount: Double) =
        repo.addExpense(categoryId, title, amount)
}