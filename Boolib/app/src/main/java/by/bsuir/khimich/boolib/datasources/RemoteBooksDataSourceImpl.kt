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
    val num_results: Int,
    val results: List<Result>,
)

@Serializable
data class Result(
    val list_name: String,
    val display_name: String,
    val list_name_encoded: String,
    val oldest_published_date: String,
    val newest_published_date: String,
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
                it.list_name,
                false,
                0,
                it.display_name.split(" "),
                id = UUID.nameUUIDFromBytes(it.list_name.toByteArray())
            )
        })
    }

    override fun getOneBook(): Flow<Book?> {
        TODO("Not yet implemented")
    }

    internal companion object {
        const val baseUri = "https://api.nytimes.com/svc/books/v3"
        const val apiKey1 = "iTy9YmAlXPzXrCT"
        const val apiKey2 = "87pPFFT4cxO8jYyIq"
    }
}
