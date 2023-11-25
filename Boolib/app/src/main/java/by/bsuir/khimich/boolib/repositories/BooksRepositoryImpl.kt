package by.bsuir.khimich.boolib.repositories

import by.bsuir.khimich.boolib.datasources.BooksDataSource
import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

internal class BooksRepositoryImpl(private val dataSource: BooksDataSource) : BooksRepository {
    override fun getAllBooks(): Flow<List<Book>> {
        return dataSource.getBooks()
    }

    override fun getBook(id: UUID?): Flow<Book?> {
        if (id == null) {
            return flowOf(null)
        }
        return dataSource.getBook(id)
    }

    override suspend fun upsert(book: Book?) {
        if (book != null) {
            dataSource.upsert(book)
        }
    }

    override suspend fun delete(id: UUID?) {
        if (id != null) {
            dataSource.delete(id)
        }
    }
}
