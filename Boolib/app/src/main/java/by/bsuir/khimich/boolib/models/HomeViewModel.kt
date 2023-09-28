package by.bsuir.khimich.boolib.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.util.UUID

data class Book(
    val name: String,
    val isRead: Boolean,
    val lastPaper: Int,
    val authors: List<String>,
    val id: UUID = UUID.randomUUID(),
)

class HomeViewModel : ViewModel() {

    val items: SnapshotStateList<Book> = DefaultBooks.toMutableStateList()

    fun onClickRemoveBook(book: Book) = items.remove(book)
    fun onClickAddBook(book: Book) = items.add(book)
    fun onClickChangeBook(book: Book) = items.add(book)

    private companion object {

        private val DefaultBooks = listOf(
            Book("Chomsky", false, 0, listOf("Robin")),
            Book("Midnight", false, 0, listOf("Hoodwink"))
        )
    }
}