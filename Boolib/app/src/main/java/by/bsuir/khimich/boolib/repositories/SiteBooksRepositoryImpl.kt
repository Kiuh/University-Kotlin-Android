package by.bsuir.khimich.boolib.repositories

import by.bsuir.khimich.boolib.datasources.RemoteBooksDataSource
import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import java.util.*

internal class SiteBooksRepositoryImpl(private val dataSource: RemoteBooksDataSource) : SiteBooksRepository {
    override fun getOneBook(id: UUID): Flow<Book?> {
        return dataSource.getOneBook()
    }

    override fun getAllBooks(): Flow<List<Book>> {
        return dataSource.getBooks()
    }
}
