package app.netlify.dev4rju9.expensetracker.domain.repository

import app.netlify.dev4rju9.expensetracker.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesForMonth(month: String): Flow<List<Category>>
    fun searchCategories(query: String): Flow<List<Category>>
    suspend fun addCategory(name: String)
}