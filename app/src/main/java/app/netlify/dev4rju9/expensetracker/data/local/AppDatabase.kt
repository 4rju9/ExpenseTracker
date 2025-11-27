package app.netlify.dev4rju9.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.netlify.dev4rju9.expensetracker.data.local.dao.CategoryDao
import app.netlify.dev4rju9.expensetracker.data.local.dao.ExpenseDao
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity

@Database(
    entities = [
        CategoryEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val expenseDao: ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_db"
    }
}