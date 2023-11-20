package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

object InMemoryBooksDataSource : BooksDataSource {

    private val books = listOf(
        Book("Chomsky", false, 0, listOf("Robin", "Goblin")),
        Book("Midnight", false, 0, listOf("Hoodwink", "Goode wink"))
    ).associateBy { it.id }.toMutableMap()

    private val _bookFlow = MutableSharedFlow<Map<UUID, Book>>(1)

    init {
        runBlocking {
            launch {
                delay(1000L)
                _bookFlow.emit(books)
            }
        }
    }

    override fun getBooks(): Flow<List<Book>> {
        return _bookFlow.asSharedFlow().onStart { delay(1000L) }.map { x -> x.values.toList() }
    }

    override fun getBook(id: UUID): Flow<Book?> {
        return _bookFlow.asSharedFlow().onStart { delay(1000L) }.map { x -> x[id] }
    }

    override suspend fun upsert(book: Book) {
        _bookFlow.emit(books.apply {
            put(book.id, book)
        })
    }

    override suspend fun delete(id: UUID) {
        _bookFlow.emit(books.apply {
            remove(id)
        })
    }
}
