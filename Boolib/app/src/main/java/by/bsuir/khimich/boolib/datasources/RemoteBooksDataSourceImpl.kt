package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import java.util.*

internal class RemoteBooksDataSourceImpl(private val client: HttpClient) : RemoteBooksDataSource {
    override fun getBooks(): Flow<List<Book>> {
        TODO("Not yet implemented")
    }

    override fun getBook(id: UUID): Flow<Book?> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(book: Book) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }
}
