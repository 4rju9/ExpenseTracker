package app.netlify.dev4rju9.expensetracker.ui.carddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import app.netlify.dev4rju9.expensetracker.domain.usecase.AddExpenseUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetBillingDay
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetExpensesForCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetMonthlyTotalUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.UpdateCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.UpdateExpenseUseCase
import app.netlify.dev4rju9.expensetracker.util.Utility.getCycleRangeFromMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailViewModel(
    private val getExpenses: GetExpensesForCategoryUseCase,
    private val addExpense: AddExpenseUseCase,
    private val getMonthlyTotal: GetMonthlyTotalUseCase,
    private val getCategory: GetCategoryUseCase,
    private val updateCategory: UpdateCategoryUseCase,
    private val updateExpense: UpdateExpenseUseCase,
    private val getBillingDay: GetBillingDay
) : ViewModel() {

    private val _billingDay = MutableStateFlow(1)
    val billingDay: StateFlow<Int> = _billingDay.asStateFlow()

    private var _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private var _selectedExpense = MutableStateFlow<Expense?>(null)
    val selectedExpense: StateFlow<Expense?> = _selectedExpense.asStateFlow()

    init {
        loadBillingDay()
    }

    suspend fun get(categoryId: Long) = getCategory(categoryId)

    fun onExpenseSelected (expense: Expense?) {
        _selectedExpense.update { expense }
    }

    fun toggleDialogState (state: Boolean) {
        _showDialog.update { state }
    }

    fun loadBillingDay () {
        _billingDay.update { getBillingDay() }
    }

    fun expenses(categoryId: Long, month: String, billingDay: Int) = flow {
        val (start, end) = getCycleRangeFromMonth(month, billingDay)
        emitAll(getExpenses(categoryId, start, end))
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun total(categoryId: Long, month: String, billingDay: Int) = flow {
        val (start, end) = getCycleRangeFromMonth(month, billingDay)
        emitAll(getMonthlyTotal(categoryId, start, end))
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun add(categoryId: Long, title: String, amount: Double, expense: Expense?) {
        if (title.isEmpty()) return
        viewModelScope.launch {
            val newTitle = title.replaceFirstChar { it.uppercaseChar() }

            val category = getCategory(categoryId)
            val newTotal = category.total + amount - (expense?.amount ?: 0.0)

            expense?.let {
                updateExpense(it.copy(title = newTitle, amount = amount))
            } ?: run { addExpense(categoryId, newTitle, amount) }

            updateCategory(category.copy(total = newTotal))
        }
    }
}