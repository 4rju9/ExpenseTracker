package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository

class GetMonthlyTotalUseCase(private val repo: ExpenseRepository) {
    operator fun invoke(categoryId: Long, start: Long, end: Long) =
        repo.getMonthlyTotal(categoryId, start, end)
}