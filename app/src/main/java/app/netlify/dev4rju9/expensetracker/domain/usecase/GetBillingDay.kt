package app.netlify.dev4rju9.expensetracker.domain.usecase

import app.netlify.dev4rju9.expensetracker.domain.repository.SettingsRepository

class GetBillingDay (private val repo: SettingsRepository) {
    operator fun invoke () = repo.getBillingCycleDay()
}