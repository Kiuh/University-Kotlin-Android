package by.bsuir.khimich.boolib.datasources

import kotlinx.coroutines.flow.Flow
import java.util.*

interface WhiteListDataSource {

    fun checkIfFavorite(): Flow<Boolean?>
    suspend fun addToFavorites(id: UUID)
    suspend fun addFromFavorites(id: UUID)
}
