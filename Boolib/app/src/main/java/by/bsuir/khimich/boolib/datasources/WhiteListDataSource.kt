package by.bsuir.khimich.boolib.datasources

import kotlinx.coroutines.flow.Flow
import java.util.*

interface WhiteListDataSource {

    fun checkIfFavorite(id: UUID): Flow<Boolean>
    suspend fun addToFavorites(id: UUID)
    suspend fun removeFromFavorites(id: UUID)
}
