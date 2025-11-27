package app.netlify.dev4rju9.expensetracker.domain.repository

import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(categoryId: Long, month: String): Flow<List<Expense>>
    fun getMonthlyTotal(categoryId: Long, month: String): Flow<Double>
    suspend fun addExpense(categoryId: Long, title: String, amount: Double)
}