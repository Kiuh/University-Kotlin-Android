package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.khimich.boolib.repositories.BooksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*

sealed interface HomeState {
    data object Loading : HomeState
    data class DisplayingBooks(val books: List<Book>) : HomeState
}

class HomeViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    val state: StateFlow<HomeState> = booksRepository.getAllBooks()
        .map(HomeState::DisplayingBooks).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState.Loading
        )

    fun onBookRemove(id: UUID?) {
        viewModelScope.launch { booksRepository.delete(id) }
    }
}
