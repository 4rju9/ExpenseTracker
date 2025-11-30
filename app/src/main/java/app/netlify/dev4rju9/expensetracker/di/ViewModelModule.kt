package app.netlify.dev4rju9.expensetracker.di

import app.netlify.dev4rju9.expensetracker.ui.carddetails.CardDetailViewModel
import app.netlify.dev4rju9.expensetracker.ui.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get(), get(), get()) }
    viewModel { CardDetailViewModel(get(), get(), get(), get(), get()) }
}