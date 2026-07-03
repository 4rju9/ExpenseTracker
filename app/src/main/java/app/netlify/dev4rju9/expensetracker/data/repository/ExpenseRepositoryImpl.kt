package app.netlify.dev4rju9.expensetracker.data.repository

import app.netlify.dev4rju9.expensetracker.data.local.dao.ExpenseDao
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository
import app.netlify.dev4rju9.expensetracker.util.Utility.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getExpenses(categoryId: Long, start: Long, end: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesForCategory(categoryId, start, end)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getMonthlyTotal(categoryId: Long, start: Long, end: Long): Flow<Double> {
        return expenseDao.getMonthlyTotal(categoryId, start, end)
            .map { it ?: 0.0 }
    }

    override suspend fun addExpense(categoryId: Long, title: String, amount: Double) {
        val timestamp = System.currentTimeMillis()

        val expense = ExpenseEntity(
            categoryId = categoryId,
            title = title,
            amount = amount,
            timestamp = timestamp
        )

        expenseDao.insertExpense(expense)
    }

    override suspend fun updateExpense(
        expense: Expense
    ) {
        val timestamp = System.currentTimeMillis()
        val expense = ExpenseEntity(
            id = expense.id,
            categoryId = expense.categoryId,
            title = expense.title,
            amount = expense.amount,
            timestamp = timestamp
        )
        expenseDao.updateExpense(expense)
    }
}