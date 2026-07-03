package app.netlify.dev4rju9.expensetracker.domain.repository

import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(categoryId: Long, start: Long, end: Long): Flow<List<Expense>>
    fun getMonthlyTotal(categoryId: Long, start: Long, end: Long): Flow<Double>
    suspend fun addExpense(categoryId: Long, title: String, amount: Double)
    suspend fun updateExpense(expense: Expense)
}