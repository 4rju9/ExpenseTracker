package app.netlify.dev4rju9.expensetracker.domain.repository

interface SettingsRepository {
    fun getBillingCycleDay () : Int
    fun setBillingCycleDay (day: Int)
}