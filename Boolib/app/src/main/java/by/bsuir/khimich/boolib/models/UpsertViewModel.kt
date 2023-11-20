package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.BooksRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

sealed interface UpsertState {
    data object Loading : UpsertState
    data class DisplayingBook(val book: Book?) : UpsertState
}

object UpsertViewModel : ViewModel() {

    private val booksRepository: BooksRepository = BooksRepositoryImpl

    val state = MutableStateFlow<UpsertState>(UpsertState.Loading)
    fun setStateFlow(id: UUID?) {
        viewModelScope.launch {
            booksRepository.getBook(id).collect { book ->
                state.value = UpsertState.DisplayingBook(book)
            }
        }
    }

    suspend fun onEntered(id: UUID?): Flow<Book?>? {
        return if (id == null)
            null
        else
            booksRepository.getBook(id)
    }

    suspend fun onUpsert(book: Book?) = booksRepository.upsert(book)

    suspend fun onDelete(id: UUID?) = booksRepository.delete(id)
}
