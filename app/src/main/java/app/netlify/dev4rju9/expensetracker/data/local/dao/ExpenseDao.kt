package app.netlify.dev4rju9.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("""
        SELECT * FROM expenses 
        WHERE categoryId = :categoryId 
        AND strftime('%Y-%m', datetime(timestamp/1000, 'unixepoch')) = :month
        ORDER BY id DESC
    """)
    fun getExpensesForCategory(categoryId: Long, month: String): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("""
        SELECT SUM(amount) FROM expenses 
        WHERE categoryId = :categoryId
        AND strftime('%Y-%m', datetime(timestamp/1000, 'unixepoch')) = :month
    """)
    fun getMonthlyTotal(categoryId: Long, month: String): Flow<Double?>
}