package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository

class GetCategoryUseCase (private val repo: CategoryRepository) {
    suspend operator fun invoke(categoryId: Long) = repo.getCategory(categoryId)
}