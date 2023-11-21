package by.bsuir.khimich.boolib.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import by.bsuir.khimich.boolib.database.entities.BookEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal interface BookDao {
    @Query("SELECT * FROM ${BookEntity.TableName}")
    fun getBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM ${BookEntity.TableName} WHERE id = :id")
    fun getBook(id: UUID): Flow<BookEntity>

    @Upsert
    suspend fun save(e: BookEntity)

    @Query("DELETE FROM ${BookEntity.TableName} WHERE id = :id")
    suspend fun delete(id: UUID)
}
