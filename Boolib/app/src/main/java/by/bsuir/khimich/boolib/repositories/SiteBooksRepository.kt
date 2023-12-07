package by.bsuir.khimich.boolib.repositories

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import java.util.*

interface SiteBooksRepository {
    fun getOneBook(id: UUID): Flow<Book?>
    fun getAllBooks(): Flow<List<Book>>
}
