package app.netlify.dev4rju9.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("""
        SELECT * FROM categories
        WHERE createdAt BETWEEN :start AND :end
        ORDER BY id DESC
    """)
    fun getCategoriesForMonth(start: Long, end: Long): Flow<List<CategoryEntity>>

    @Insert
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query(
        """
        SELECT * FROM categories
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%')
        AND createdAt BETWEEN :start AND :end
        ORDER BY id DESC
    """
    )
    fun searchCategories(query: String, start: Long, end: Long): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategory(categoryId: Long): CategoryEntity

    @Update
    suspend fun updateCategory(category: CategoryEntity)

}