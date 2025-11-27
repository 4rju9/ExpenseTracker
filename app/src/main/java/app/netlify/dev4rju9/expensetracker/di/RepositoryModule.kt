package app.netlify.dev4rju9.expensetracker.di

import app.netlify.dev4rju9.expensetracker.data.repository.CategoryRepositoryImpl
import app.netlify.dev4rju9.expensetracker.data.repository.ExpenseRepositoryImpl
import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository
import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
}