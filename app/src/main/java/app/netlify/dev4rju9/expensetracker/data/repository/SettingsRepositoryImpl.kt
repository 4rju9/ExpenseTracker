package app.netlify.dev4rju9.expensetracker.data.repository

import android.content.Context
import app.netlify.dev4rju9.expensetracker.domain.repository.SettingsRepository
import app.netlify.dev4rju9.expensetracker.util.getBillingCycleDay
import app.netlify.dev4rju9.expensetracker.util.saveBillingCycleDay

class SettingsRepositoryImpl (private val context: Context) : SettingsRepository {
    override fun getBillingCycleDay() = context.getBillingCycleDay()
    override fun setBillingCycleDay(day: Int) = context.saveBillingCycleDay(day)
}