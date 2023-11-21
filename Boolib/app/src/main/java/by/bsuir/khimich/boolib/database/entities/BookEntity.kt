package by.bsuir.khimich.boolib.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = BookEntity.TableName)
data class BookEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val isRead: Boolean,
    val lastPaper: Int,
    val authors: String,
) {
    internal companion object {
        const val TableName = "books"
    }
}
