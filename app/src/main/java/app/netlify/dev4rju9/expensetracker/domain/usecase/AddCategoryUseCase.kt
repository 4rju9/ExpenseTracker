package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository

class AddCategoryUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke(name: String) = repo.addCategory(name)
}