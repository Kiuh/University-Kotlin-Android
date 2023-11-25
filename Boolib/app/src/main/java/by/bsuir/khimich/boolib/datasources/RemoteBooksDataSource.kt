package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow

interface RemoteBooksDataSource {
    fun getBooks(): Flow<List<Book>>
}
