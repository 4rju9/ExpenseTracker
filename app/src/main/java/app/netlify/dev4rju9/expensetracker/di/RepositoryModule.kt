package app.netlify.dev4rju9.expensetracker.di

import app.netlify.dev4rju9.expensetracker.data.repository.CategoryRepositoryImpl
import app.netlify.dev4rju9.expensetracker.data.repository.ExpenseRepositoryImpl
import app.netlify.dev4rju9.expensetracker.data.repository.SettingsRepositoryImpl
import app.netlify.dev4rju9.expensetracker.domain.repository.CategoryRepository
import app.netlify.dev4rju9.expensetracker.domain.repository.ExpenseRepository
import app.netlify.dev4rju9.expensetracker.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
}