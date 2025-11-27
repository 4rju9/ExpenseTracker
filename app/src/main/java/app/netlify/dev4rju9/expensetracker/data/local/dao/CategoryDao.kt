package app.netlify.dev4rju9.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE createdMonth = :month ORDER BY id DESC")
    fun getCategoriesForMonth(month: String): Flow<List<CategoryEntity>>

    @Insert
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :query || '%'")
    fun searchCategories(query: String): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategory(categoryId: Long): CategoryEntity

    @Update
    suspend fun updateCategory(category: CategoryEntity)

}