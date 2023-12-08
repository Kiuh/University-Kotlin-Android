package by.bsuir.khimich.boolib.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = WhiteListEntity.TableName)
data class WhiteListEntity(
    @PrimaryKey val id: UUID,
    val saved: UUID,
) {
    internal companion object {
        const val TableName = "white_list"
    }
}
