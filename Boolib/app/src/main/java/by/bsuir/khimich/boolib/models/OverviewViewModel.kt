package by.bsuir.khimich.boolib.models

import by.bsuir.khimich.boolib.mviframework.MVIViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.SiteBooksRepository
import by.bsuir.khimich.boolib.repositories.WhiteListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*

sealed interface OverviewState {
    data object Loading : OverviewState
    data class DisplayingSiteBook(val book: Book, val inFavorites: Boolean) : OverviewState
    data class DisplayingPrivateBook(val book: Book, val inFavorites: Boolean) : OverviewState
    data class Error(val e: Exception?) : OverviewState
}

sealed interface OverviewIntent {
    data object ExitClicked : OverviewIntent
    data class AddToFavoritesClicked(val id: UUID) : OverviewIntent
    data class RemoveFromFavoritesClicked(val id: UUID) : OverviewIntent
}

sealed interface OverviewEvent {
    data object Exit : OverviewEvent
}

enum class OverviewMode {
    FromSite,
    FromLocal
}

class OverviewViewModel(
    private val id: UUID,
    private val mode: OverviewMode,
    private val siteBooksRepository: SiteBooksRepository,
    private val booksRepository: BooksRepository,
    private val whiteListRepository: WhiteListRepository,
) : MVIViewModel<OverviewState, OverviewIntent, OverviewEvent>(OverviewState.Loading) {

    override fun CoroutineScope.onSubscribe() {
        when (mode) {
            OverviewMode.FromLocal -> {
                booksRepository.getBook(id)
                    .combine(whiteListRepository.checkIfFavorite(id)) { book, check ->
                        object {
                            val book: Book? = book
                            val check: Boolean? = check
                        }
                    }
                    .onEach {
                        state {
                            if (it.book != null && it.check != null) {
                                OverviewState.DisplayingPrivateBook(it.book, it.check)
                            }
                            OverviewState.Error(null)
                        }
                    }
                    .catch {
                        OverviewState.Error(it as? Exception)
                    }
                    .flowOn(Dispatchers.Default)
                    .launchIn(this)
            }

            OverviewMode.FromSite -> {
                siteBooksRepository.getOneBook(id)
                    .combine(whiteListRepository.checkIfFavorite(id)) { book, check ->
                        object {
                            val book: Book? = book
                            val check: Boolean? = check
                        }
                    }
                    .onEach {
                        state {
                            if (it.book != null && it.check != null) {
                                OverviewState.DisplayingSiteBook(it.book, it.check)
                            }
                            OverviewState.Error(null)
                        }
                    }
                    .catch {
                        OverviewState.Error(it as? Exception)
                    }
                    .flowOn(Dispatchers.Default)
                    .launchIn(this)
            }
        }
    }

    override suspend fun reduce(intent: OverviewIntent) {
        when (intent) {
            is OverviewIntent.ExitClicked -> {
                event(OverviewEvent.Exit)
            }

            is OverviewIntent.AddToFavoritesClicked -> {
                whiteListRepository.addToFavorites(intent.id)
            }

            is OverviewIntent.RemoveFromFavoritesClicked -> {
                whiteListRepository.removeFromFavorites(intent.id)
            }
        }
    }
}
