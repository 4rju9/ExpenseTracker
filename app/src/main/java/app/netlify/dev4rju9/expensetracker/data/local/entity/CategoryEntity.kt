package app.netlify.dev4rju9.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.netlify.dev4rju9.expensetracker.ui.theme.BabyBlue
import app.netlify.dev4rju9.expensetracker.ui.theme.LightGreen
import app.netlify.dev4rju9.expensetracker.ui.theme.RedOrange
import app.netlify.dev4rju9.expensetracker.ui.theme.RedPink
import app.netlify.dev4rju9.expensetracker.ui.theme.Violet

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val total: Double,
    val color: Int,
    val createdMonth: String // "2025-01"
) {

    companion object {
        val categoryColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

}