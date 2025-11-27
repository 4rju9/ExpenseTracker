package app.netlify.dev4rju9.expensetracker.data.repository

import app.netlify.dev4rju9.expensetracker.data.local.dao.CategoryDao
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository
import app.netlify.dev4rju9.expensetracker.util.Utility.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getCategoriesForMonth(month: String): Flow<List<Category>> {
        return categoryDao.getCategoriesForMonth(month)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun searchCategories(query: String): Flow<List<Category>> {
        return categoryDao.searchCategories(query)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addCategory(name: String) {
        val currentMonth = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val entity = CategoryEntity(
            name = name,
            createdMonth = currentMonth
        )
        categoryDao.insertCategory(entity)
    }
}