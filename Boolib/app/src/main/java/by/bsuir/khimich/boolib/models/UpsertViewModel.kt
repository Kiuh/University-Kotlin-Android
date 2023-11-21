package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.khimich.boolib.repositories.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

sealed interface UpsertState {
    data object Loading : UpsertState
    data class DisplayingBook(val book: Book?) : UpsertState
}

class UpsertViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    val state = MutableStateFlow<UpsertState>(UpsertState.Loading)
    fun setStateFlow(id: UUID?) {
        viewModelScope.launch {
            booksRepository.getBook(id).collect { book ->
                state.value = UpsertState.DisplayingBook(book)
            }
        }
    }

    suspend fun onUpsert(book: Book?) = booksRepository.upsert(book)

    suspend fun onDelete(id: UUID?) = booksRepository.delete(id)
}
