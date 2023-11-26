package by.bsuir.khimich.boolib.models

import by.bsuir.khimich.boolib.mviframework.MVIViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.SiteBooksRepository
import java.util.*

sealed interface ExtendState {
    data object Loading : ExtendState
    data class DisplayingSiteBook(val id: UUID) : ExtendState
    data class DisplayingPrivateBook(val id: UUID) : ExtendState
    data class DisplayingDeletedBook(val id: UUID) : ExtendState
}

sealed interface ExtendIntent {
    data object ExitClicked : ExtendIntent
    data class DeleteClicked(val id: UUID) : ExtendIntent
    data class AddToLocalClicked(val id: UUID) : ExtendIntent
    data class RemoveFromLocalClicked(val id: UUID) : ExtendIntent
}

sealed interface ExtendAction {
    data object Exit : ExtendAction
    data class Delete(val id: UUID) : ExtendAction
    data class AddToLocal(val id: UUID) : ExtendAction
    data class RemoveFromLocal(val id: UUID) : ExtendAction
}

class ExtendViewModel(
    siteBooksRepository: SiteBooksRepository, private val booksRepository: BooksRepository,
) : MVIViewModel<ExtendState, ExtendIntent, ExtendAction>(ExtendState.Loading) {
    override suspend fun reduce(intent: ExtendIntent) {
        when (intent) {
            is ExtendIntent.ExitClicked -> event(ExtendAction.Exit)

            is ExtendIntent.DeleteClicked -> {
                event(ExtendAction.Delete(intent.id))
                state { ExtendState.DisplayingDeletedBook(intent.id) }
            }

            is ExtendIntent.AddToLocalClicked -> {
                event(ExtendAction.AddToLocal(intent.id))
                state { ExtendState.DisplayingPrivateBook(intent.id) }
            }

            is ExtendIntent.RemoveFromLocalClicked -> {
                event(ExtendAction.RemoveFromLocal(intent.id))
                state { ExtendState.DisplayingSiteBook(intent.id) }
            }
        }
    }
}

