package app.netlify.dev4rju9.expensetracker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.expensetracker.domain.usecase.AddCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetCategoriesForMonthUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.SearchCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardViewModel(
    private val getCategoriesForMonth: GetCategoriesForMonthUseCase,
    private val addCategory: AddCategoryUseCase,
    private val searchCategories: SearchCategoriesUseCase
) : ViewModel() {

    private val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
    private val _selectedMonth = MutableStateFlow(currentMonth)
    val selectedMonth = _selectedMonth.asStateFlow()

    val categories = selectedMonth.flatMapLatest {
        getCategoriesForMonth(it)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun nextMonth() {
        // cannot exceed current month
        if (_selectedMonth.value != currentMonth) {
            _selectedMonth.value = LocalDate.parse(_selectedMonth.value + "-01")
                .plusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM"))
        }
    }

    fun previousMonth() {
        _selectedMonth.value = LocalDate.parse(_selectedMonth.value + "-01")
            .minusMonths(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM"))
    }

    fun addNewCategory(name: String) = viewModelScope.launch {
        addCategory(name)
    }

    fun search(query: String) = searchCategories(query)
}