package by.bsuir.khimich.boolib.models

import java.util.*

data class Book(
    val name: String,
    val isRead: Boolean,
    val lastPaper: Int,
    val authors: List<String>,
    val id: UUID = UUID.randomUUID(),
) {
    companion object {
        fun getNotFoundBook(): Book {
            return Book(
                name = "Not Found",
                isRead = false,
                lastPaper = 0,
                authors = listOf("Not found author"),
            )
        }
    }
}
