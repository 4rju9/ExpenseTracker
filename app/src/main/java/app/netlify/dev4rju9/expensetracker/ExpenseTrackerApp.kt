package app.netlify.dev4rju9.expensetracker

import android.app.Application
import app.netlify.dev4rju9.expensetracker.di.databaseModule
import app.netlify.dev4rju9.expensetracker.di.repositoryModule
import app.netlify.dev4rju9.expensetracker.di.useCaseModule
import app.netlify.dev4rju9.expensetracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ExpenseTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ExpenseTrackerApp)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}