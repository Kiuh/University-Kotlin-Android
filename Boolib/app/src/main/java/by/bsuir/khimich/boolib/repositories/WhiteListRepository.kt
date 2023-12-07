package by.bsuir.khimich.boolib.repositories

import kotlinx.coroutines.flow.Flow
import java.util.*

interface WhiteListRepository {
    fun checkIfFavorite(id: UUID): Flow<Boolean?>
    suspend fun addToFavorites(id: UUID)
    suspend fun removeFromFavorites(id: UUID)
}
