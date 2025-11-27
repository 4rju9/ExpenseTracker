package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository

class GetExpensesForCategoryUseCase(private val repo: ExpenseRepository) {
    operator fun invoke(categoryId: Long, month: String) =
        repo.getExpenses(categoryId, month)
}