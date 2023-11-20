package by.bsuir.khimich.boolib.screens.upsert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.khimich.boolib.models.UpsertViewModel
import by.bsuir.khimich.boolib.screens.destinations.HomeDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.util.*

@Destination
@Composable
fun Upsert(destinationsNavigator: DestinationsNavigator, id: UUID?) =
    UpsertCover(destinationsNavigator = destinationsNavigator, id = id)

@Composable
fun UpsertCover(destinationsNavigator: DestinationsNavigator, id: UUID?) {
    val viewModel = viewModel<UpsertViewModel>()

    viewModel.setStateFlow(id)
    val state by viewModel.state.collectAsState()

    UpsertScreen(
        onSave = { book ->
            viewModel.viewModelScope.launch {
                viewModel.onUpsert(book)
                destinationsNavigator.navigate(HomeDestination())
            }
        },
        onDelete = {
            viewModel.viewModelScope.launch {
                viewModel.onDelete(id)
                destinationsNavigator.navigate(HomeDestination())
            }
        },
        upsertState = state
    )
}
