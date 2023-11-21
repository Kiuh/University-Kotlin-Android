package by.bsuir.khimich.boolib.datasources

import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.database.entities.BookEntity
import by.bsuir.khimich.boolib.models.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

fun transformToBook(bookEntity: BookEntity?): Book {
    return if (bookEntity != null) Book(
        bookEntity.name,
        bookEntity.isRead,
        bookEntity.lastPaper,
        bookEntity.authors.split("\n"),
        bookEntity.id
    ) else Book.getNotFoundBook()
}

fun transformToBookEntity(book: Book): BookEntity {
    return BookEntity(
        book.id,
        book.name,
        book.isRead,
        book.lastPaper,
        book.authors.joinToString { "\n" }
    )
}

internal class BooksDataSourceImpl(private val dao: BookDao) : BooksDataSource {
    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks().map {
            it.map { bookEntity ->
                transformToBook(bookEntity)
            }
        }
    }

    override fun getBook(id: UUID): Flow<Book?> {
        return dao.getBook(id).map { bookEntity ->
            transformToBook(bookEntity)
        }
    }

    override suspend fun upsert(book: Book) {
        dao.save(transformToBookEntity(book))
    }

    override suspend fun delete(id: UUID) {
        dao.delete(id)
    }
}
/*
object InMemoryBooksDataSource : BooksDataSource {


    private val books = listOf(
        Book("Chomsky", false, 0, listOf("Robin", "Goblin")),
        Book("Midnight", false, 0, listOf("Hoodwink", "Goode wink"))
    ).associateBy { it.id }.toMutableMap()

    private val _bookFlow = MutableSharedFlow<Map<UUID, Book>>(1)

    init {
        runBlocking {
            launch {
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
*/
