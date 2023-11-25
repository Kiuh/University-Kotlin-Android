package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
data class BookList(
    val status: String,
    val copyright: String,
    val numResults: Int,
    val results: List<Result>,
)

@Serializable
data class Result(
    val listName: String,
    val displayName: String,
    val listNameEncoded: String,
    val oldestPublishedDate: String,
    val newestPublishedDate: String,
    val updated: String,
)

internal class RemoteBooksDataSourceImpl(private val client: HttpClient) : RemoteBooksDataSource {
    override fun getBooks(): Flow<List<Book>> = flow {
        val url = "$baseUri/lists/names.json?api-key=$apiKey1$apiKey2"
        val response = client.get {
            url(url)
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer token")
        }
        val bookList = Json.decodeFromString<BookList>(String(response.readBytes()))
        emit(bookList.results.map {
            Book(
                it.listName,
                false,
                0,
                listOf("No Authors"),
                id = UUID.nameUUIDFromBytes(it.listName.toByteArray())
            )
        })
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

    internal companion object {
        const val baseUri = "https://api.nytimes.com/svc/books/v3"
        const val apiKey1 = "iTy9YmAlXPzXrCT"
        const val apiKey2 = "87pPFFT4cxO8jYyIq"
    }
}
