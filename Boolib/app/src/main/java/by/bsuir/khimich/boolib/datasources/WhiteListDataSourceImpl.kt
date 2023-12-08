package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.database.daos.WhiteListDao
import by.bsuir.khimich.boolib.database.entities.WhiteListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

internal class WhiteListDataSourceImpl(private val dao: WhiteListDao) : WhiteListDataSource {
    override fun checkIfFavorite(id: UUID): Flow<Boolean> {
        return dao.checkIfFavorite(id).map { it != null }
    }

    override suspend fun addToFavorites(id: UUID) {
        dao.save(WhiteListEntity(UUID.randomUUID(), id))
    }

    override suspend fun removeFromFavorites(id: UUID) {
        dao.delete(id)
    }
}
