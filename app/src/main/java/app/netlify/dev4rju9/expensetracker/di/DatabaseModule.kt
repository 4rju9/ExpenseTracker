package app.netlify.dev4rju9.expensetracker.di

import androidx.room.Room
import app.netlify.dev4rju9.expensetracker.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "expense_db"
        ).build()
    }
    single { get<AppDatabase>().categoryDao }
    single { get<AppDatabase>().expenseDao }
}