package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository

class SearchCategoriesUseCase(private val repo: CategoryRepository) {
    operator fun invoke(query: String) = repo.searchCategories(query)
}