package app.netlify.dev4rju9.expensetracker.data.repository

import app.netlify.dev4rju9.expensetracker.data.local.dao.CategoryDao
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository
import app.netlify.dev4rju9.expensetracker.util.Utility.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getCategoriesForMonth(start: Long, end: Long): Flow<List<Category>> {
        return categoryDao.getCategoriesForMonth(start, end)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun searchCategories(query: String, start: Long, end: Long): Flow<List<Category>> {
        return categoryDao.searchCategories(query, start, end)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addCategory(name: String, color: Int) {
        val entity = CategoryEntity(
            name = name,
            total = 0.0,
            color = color,
            createdAt = System.currentTimeMillis()
        )
        categoryDao.insertCategory(entity)
    }

    override suspend fun getCategory(categoryId: Long): Category {
        return categoryDao.getCategory(categoryId).toDomain()
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(
            CategoryEntity(
                id = category.id,
                name = category.name,
                total = category.total,
                color = category.color,
                createdAt = category.createdAt
            )
        )
    }

}