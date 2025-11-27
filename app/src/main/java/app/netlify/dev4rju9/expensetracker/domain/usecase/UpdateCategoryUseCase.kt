package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository

class UpdateCategoryUseCase (private val repo: CategoryRepository) {
    suspend operator fun invoke(category: Category) = repo.updateCategory(category)
}