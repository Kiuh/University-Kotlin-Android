package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import java.util.*

interface RemoteBooksDataSource {
    fun getBooks(): Flow<List<Book>>

    fun getOneBook(id: UUID): Flow<Book?>
}
