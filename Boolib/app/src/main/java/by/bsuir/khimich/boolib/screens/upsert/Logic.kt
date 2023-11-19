package by.bsuir.khimich.boolib.screens.upsert

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.khimich.boolib.models.UpsertViewModel
import by.bsuir.khimich.boolib.screens.destinations.HomeDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@Destination
@Composable
fun Upsert(destinationsNavigator: DestinationsNavigator, id: UUID?) =
    UpsertCover(destinationsNavigator = destinationsNavigator, id = id)

@Composable
fun UpsertCover(destinationsNavigator: DestinationsNavigator, id: UUID?) {
    val viewModel = viewModel<UpsertViewModel>()
    // TODO: upsert screen
    UpsertScreen(
        onCallback = {
            if (it) {
                destinationsNavigator.navigate(HomeDestination())
            }
        },
        upsertState = viewModel.state
    )
}
