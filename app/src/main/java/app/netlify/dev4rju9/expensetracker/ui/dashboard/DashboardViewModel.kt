package app.netlify.dev4rju9.expensetracker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.usecase.AddCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetBillingDay
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetCategoriesForMonthUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.SearchCategoriesUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.SetBillingDay
import app.netlify.dev4rju9.expensetracker.domain.usecase.UpdateCategoryUseCase
import app.netlify.dev4rju9.expensetracker.util.Utility.getCycleRangeFromMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardViewModel(
    private val getCategoriesForMonth: GetCategoriesForMonthUseCase,
    private val addCategory: AddCategoryUseCase,
    private val searchCategories: SearchCategoriesUseCase,
    private val updateCategory: UpdateCategoryUseCase,
    private val getBillingDay: GetBillingDay,
    private val setBillingDay: SetBillingDay
) : ViewModel() {

    private var _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    private var _showBillingCycleDialog = MutableStateFlow(false)
    val showBillingCycleDialog: StateFlow<Boolean> = _showBillingCycleDialog.asStateFlow()
    private var _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _billingDay = MutableStateFlow(1)
    val billingDay: StateFlow<Int> = _billingDay.asStateFlow()
    private val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
    private val _selectedMonth = MutableStateFlow(currentMonth)
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchResult = combine(
        _searchQuery,
        selectedMonth,
        billingDay
    ) { query, month, billingDay ->
        Triple(query, month, billingDay)
    }.flatMapLatest { (query, month, billingDay) ->
        search(query, month, billingDay) // your existing function
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val categories = selectedMonth.combine(_billingDay) { month, billingDay ->
        getCycleRangeFromMonth(month, billingDay)
    }.flatMapLatest { (start, end) ->
        getCategoriesForMonth(start, end)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadBillingDay()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleDialogState (state: Boolean) {
        _showDialog.update { state }
    }

    fun toggleBillingDialogState (state: Boolean) {
        _showBillingCycleDialog.update { state }
    }

    fun onCategorySelected (category: Category?) {
        _selectedCategory.update { category }
    }

    fun loadBillingDay () {
        _billingDay.update { getBillingDay() }
    }

    fun saveBillingDay (day: Int) {
        setBillingDay(day)
        loadBillingDay()
    }

    fun nextMonth() {
        // cannot exceed current month
        if (_selectedMonth.value != currentMonth) {
            _selectedMonth.value = LocalDate.parse(_selectedMonth.value + "-01").plusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM"))
        }
    }

    fun previousMonth() {
        _selectedMonth.value = LocalDate.parse(_selectedMonth.value + "-01").minusMonths(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM"))
    }

    fun hasNextMonth(): Boolean {
        return _selectedMonth.value != currentMonth
    }

    fun addNewCategory(name: String, color: Int, category: Category?) = viewModelScope.launch {
        if (name.isEmpty()) return@launch
        val newName = name.replaceFirstChar { it.uppercaseChar() }
        if (categories.value.any { it.name == newName }) return@launch
        category?.let {
            updateCategory(it.copy(name = name, color = color))
        } ?: run { addCategory(newName, color) }
    }

    fun search(query: String, month: String, billingDay: Int) = flow {
        val (start, end) = getCycleRangeFromMonth(month, billingDay)
        emitAll(searchCategories(query, start, end))
    }

}