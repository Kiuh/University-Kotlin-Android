package by.bsuir.khimich.boolib.repositories

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow

interface SiteBooksRepository {

    fun getAllBooks(): Flow<List<Book>>
}
