package by.bsuir.khimich.boolib.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.SiteBooksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SiteState {
    data object Loading : SiteState
    data class DisplayingBooks(val books: List<Book>) : SiteState
}

class SiteViewModel(siteBooksRepository: SiteBooksRepository, private val booksRepository: BooksRepository) :
    ViewModel() {

    val state: StateFlow<SiteState> = siteBooksRepository.getAllBooks()
        .map(SiteState::DisplayingBooks).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SiteState.Loading
        )

    fun onBookClick(book: Book) {
        viewModelScope.launch {
            booksRepository.upsert(book)
        }
    }
}
