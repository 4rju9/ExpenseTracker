package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository

class GetCategoriesForMonthUseCase(private val repo: CategoryRepository) {
    operator fun invoke(month: String) = repo.getCategoriesForMonth(month)
}