package by.bsuir.khimich.boolib.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import by.bsuir.khimich.boolib.database.entities.WhiteListEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal interface WhiteListDao {
    @Query("SELECT * FROM ${WhiteListEntity.TableName} WHERE saved = :id")
    fun checkIfFavorite(id: UUID): Flow<WhiteListEntity?>

    @Upsert
    suspend fun save(e: WhiteListEntity)

    @Query("DELETE FROM ${WhiteListEntity.TableName} WHERE saved = :id")
    suspend fun delete(id: UUID)
}
