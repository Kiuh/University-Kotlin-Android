package by.bsuir.khimich.boolib.models

import by.bsuir.khimich.boolib.mviframework.MVIViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.SiteBooksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

sealed interface OverviewState {
    data object Loading : OverviewState
    data class DisplayingSiteBook(val book: Book, val inFavorites: Boolean) : OverviewState
    data class DisplayingPrivateBook(val book: Book, val inFavorites: Boolean) : OverviewState
}

sealed interface OverviewIntent {
    data object ExitClicked : OverviewIntent
    data class AddToFavoritesClicked(val id: UUID) : OverviewIntent
    data class RemoveFromFavoritesClicked(val id: UUID) : OverviewIntent
}

sealed interface OverviewEvent {
    data object Exit : OverviewEvent
    data class ChangeFavorites(val inFavorites: Boolean) : OverviewEvent
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
) : MVIViewModel<OverviewState, OverviewIntent, OverviewEvent>(OverviewState.Loading) {

    override fun CoroutineScope.onSubscribe() {
        when (mode) {
            OverviewMode.FromLocal -> {
                booksRepository.getBook(id)
                    .onEach {
                        state {
                            if (it != null) {
                                OverviewState.DisplayingPrivateBook(it, false)
                            }
                            OverviewState.Loading
                        }
                    }
                    .catch { }
                    .flowOn(Dispatchers.Default)
                    .launchIn(this)
            }

            OverviewMode.FromSite -> {
                siteBooksRepository.getOneBook(id)
                    .onEach {
                        state {
                            if (it != null) {
                                OverviewState.DisplayingSiteBook(it, false)
                            }
                            OverviewState.Loading
                        }
                    }
                    .catch { }
                    .flowOn(Dispatchers.Default)
                    .launchIn(this)
            }
        }
    }

    override suspend fun reduce(intent: OverviewIntent) {
        when (intent) {
            is OverviewIntent.ExitClicked -> event(OverviewEvent.Exit)

            is OverviewIntent.AddToFavoritesClicked -> {
                event(OverviewEvent.ChangeFavorites(true))
            }

            is OverviewIntent.RemoveFromFavoritesClicked -> {
                event(OverviewEvent.ChangeFavorites(false))
            }
        }
    }
}
