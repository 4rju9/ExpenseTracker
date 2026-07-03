package app.netlify.dev4rju9.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import app.netlify.dev4rju9.expensetracker.data.local.entity.CategoryEntity
import app.netlify.dev4rju9.expensetracker.data.local.entity.ExpenseEntity
import app.netlify.dev4rju9.expensetracker.domain.model.Category
import app.netlify.dev4rju9.expensetracker.domain.model.Expense
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object Utility {

    fun CategoryEntity.toDomain(): Category {
        return Category(
            id = id,
            name = name,
            total = total,
            color = color,
            createdAt = createdAt
        )
    }

    fun ExpenseEntity.toDomain(): Expense {
        return Expense(
            id = id,
            categoryId = categoryId,
            title = title,
            amount = amount,
            timestamp = timestamp
        )
    }

    fun Long.toDate() : String {
        val date = Date(this)
        return SimpleDateFormat("MMM, dd", Locale.getDefault()).format(date)
    }

    fun getCycleRangeFromMonth(
        month: String,// "YYYY-MM"
        salaryDay: Int
    ): Pair<Long, Long> {

        val zone = ZoneId.systemDefault()

        // Parse "2026-08"
        val yearMonth = YearMonth.parse(month)

        // Handle edge case (e.g., Feb + 31)
        val safeStartDay = minOf(salaryDay, yearMonth.lengthOfMonth())

        val startDate = yearMonth.atDay(safeStartDay)

        val nextMonth = yearMonth.plusMonths(1)
        val safeEndDay = minOf(salaryDay, nextMonth.lengthOfMonth())

        val endDate = nextMonth.atDay(safeEndDay).minusDays(1)

        val startMillis = startDate
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val endMillis = endDate
            .atTime(23, 59, 59)
            .atZone(zone)
            .toInstant()
            .toEpochMilli()

        return startMillis to endMillis
    }

}

object Constants {
    const val BILLING_CYCLE_DAY = "billing_cycle_day"
    const val SHARED_PREFERENCES_NAME = "ExpenseTracker"
}

fun Context.getSharedPreference (): SharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

fun Context.getBillingCycleDay () : Int {
    val sharedPreferences = getSharedPreference()
    return sharedPreferences.getInt(Constants.BILLING_CYCLE_DAY, 1)
}

fun Context.saveBillingCycleDay (day: Int) {
    val sharedPreferences = getSharedPreference()
    sharedPreferences.edit {
        putInt(Constants.BILLING_CYCLE_DAY, day)
    }
}