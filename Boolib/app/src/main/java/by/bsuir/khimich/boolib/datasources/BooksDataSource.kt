package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import java.util.*

interface BooksDataSource {

    fun getBooks(): Flow<List<Book>>
    fun getBook(id: UUID): Flow<Book?>

    suspend fun upsert(book: Book)
    suspend fun delete(id: UUID)
}
