package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository

class GetExpensesForCategoryUseCase(private val repo: ExpenseRepository) {
    operator fun invoke(categoryId: Long, start: Long, end: Long) =
        repo.getExpenses(categoryId, start, end)
}