package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.SettingsRepository

class SetBillingDay (private val repo: SettingsRepository) {
    operator fun invoke (day: Int) = repo.setBillingCycleDay(day)
}