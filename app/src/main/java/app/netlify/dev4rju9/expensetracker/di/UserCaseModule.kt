package app.netlify.dev4rju9.expensetracker.di

import app.netlify.dev4rju9.expensetracker.domain.usecase.AddCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.AddExpenseUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetCategoriesForMonthUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetExpensesForCategoryUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.GetMonthlyTotalUseCase
import app.netlify.dev4rju9.expensetracker.domain.usecase.SearchCategoriesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCategoriesForMonthUseCase(get()) }
    factory { AddCategoryUseCase(get()) }
    factory { SearchCategoriesUseCase(get()) }
    factory { GetExpensesForCategoryUseCase(get()) }
    factory { AddExpenseUseCase(get()) }
    factory { GetMonthlyTotalUseCase(get()) }
}