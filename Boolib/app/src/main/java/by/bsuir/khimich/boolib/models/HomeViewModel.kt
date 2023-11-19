package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.BooksRepositoryImpl
import kotlinx.coroutines.flow.*
import java.util.*

sealed interface HomeState {
    data object Loading : HomeState
    data class DisplayingBooks(val books: List<Book>) : HomeState
}

class HomeViewModel : ViewModel() {
    private val booksRepository: BooksRepository = BooksRepositoryImpl

    val selected = MutableStateFlow<UUID?>(null)
    val state: StateFlow<HomeState> = booksRepository.getAllBooks()
        .map(HomeState::DisplayingBooks).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState.Loading
        )

    fun onBookClick(id: UUID?) = selected.update { id }
    suspend fun onBookRemove(id: UUID?) = booksRepository.delete(id)
}
