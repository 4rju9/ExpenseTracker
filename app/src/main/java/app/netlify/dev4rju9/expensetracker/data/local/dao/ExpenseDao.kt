package app.netlify.dev4rju9.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("""
        SELECT * FROM expenses 
        WHERE categoryId = :categoryId 
        AND timestamp BETWEEN :start AND :end
        ORDER BY id DESC
    """)
    fun getExpensesForCategory(
        categoryId: Long,
        start: Long,
        end: Long
    ): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("""
        SELECT SUM(amount) FROM expenses 
        WHERE categoryId = :categoryId
        AND timestamp BETWEEN :start AND :end
    """)
    fun getMonthlyTotal(
        categoryId: Long,
        start: Long,
        end: Long
    ): Flow<Double?>

    @Update
    suspend fun updateExpense (expense: ExpenseEntity)
}