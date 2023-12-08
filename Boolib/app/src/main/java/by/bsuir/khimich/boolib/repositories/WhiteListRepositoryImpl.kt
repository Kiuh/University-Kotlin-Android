package by.bsuir.khimich.boolib.repositories

import by.bsuir.khimich.boolib.datasources.WhiteListDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

internal class WhiteListRepositoryImpl(private val dataSource: WhiteListDataSource) : WhiteListRepository {
    override fun checkIfFavorite(id: UUID?): Flow<Boolean> {
        return if (id == null) {
            flowOf(false)
        } else {
            dataSource.checkIfFavorite(id)
        }
    }

    override suspend fun addToFavorites(id: UUID?) {
        if (id != null) {
            dataSource.addToFavorites(id)
        }
    }

    override suspend fun removeFromFavorites(id: UUID?) {
        if (id != null) {
            dataSource.removeFromFavorites(id)
        }
    }
}
