package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.BooksRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface UpsertState {
    data object Loading : UpsertState
    data class DisplayingBook(val book: Book?) : UpsertState
}

object UpsertViewModel : ViewModel() {

    private val _state = MutableStateFlow<UpsertState>(UpsertState.DisplayingBook(null))
    val state: StateFlow<UpsertState> = _state.asStateFlow()

    fun updateState(newState: UpsertState) {
        _state.value = newState
    }

    val booksRepository: BooksRepository = BooksRepositoryImpl
}
