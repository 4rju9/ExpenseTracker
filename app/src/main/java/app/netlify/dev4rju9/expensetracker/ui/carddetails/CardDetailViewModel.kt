package app.netlify.dev4rju9.expensetracker.ui.carddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.usecase.AddExpenseUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetExpensesForCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetMonthlyTotalUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.UpdateCategoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CardDetailViewModel(
    private val getExpenses: GetExpensesForCategoryUseCase,
    private val addExpense: AddExpenseUseCase,
    private val getMonthlyTotal: GetMonthlyTotalUseCase,
    private val getCategory: GetCategoryUseCase,
    private val updateCategory: UpdateCategoryUseCase
) : ViewModel() {

    suspend fun get (categoryId: Long) = getCategory(categoryId)

    fun expenses(categoryId: Long, month: String) =
        getExpenses(categoryId, month)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun total(categoryId: Long, month: String) =
        getMonthlyTotal(categoryId, month)
            .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun add(categoryId: Long, title: String, amount: Double) {
        if (title.isEmpty()) return
        viewModelScope.launch {
            val newTitle = title.replaceFirstChar { it.uppercaseChar() }
            addExpense(categoryId, newTitle, amount)
            val category = getCategory(categoryId)
            val newTotal = category.total + amount
            updateCategory(category.copy(total = newTotal))
        }
    }
}