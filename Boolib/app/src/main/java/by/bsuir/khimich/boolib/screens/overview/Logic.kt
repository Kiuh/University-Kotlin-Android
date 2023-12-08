package by.bsuir.khimich.boolib.screens.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import by.bsuir.khimich.boolib.models.OverviewEvent
import by.bsuir.khimich.boolib.models.OverviewMode
import by.bsuir.khimich.boolib.models.OverviewViewModel
import by.bsuir.khimich.boolib.mviframework.container
import by.bsuir.khimich.boolib.mviframework.subscribe
import by.bsuir.khimich.boolib.screens.destinations.HomeDestination
import by.bsuir.khimich.boolib.screens.destinations.SiteDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.core.parameter.parametersOf
import java.util.*

@Destination
@Composable
fun Overview(
    destinationsNavigator: DestinationsNavigator,
    id: UUID?,
    mode: OverviewMode,
) = OverviewCover(destinationsNavigator = destinationsNavigator, id = id, mode = mode)

@Composable
fun OverviewCover(
    destinationsNavigator: DestinationsNavigator,
    id: UUID?,
    mode: OverviewMode,
) {
    val viewModel = container<OverviewViewModel, _, _, _> { parametersOf(id, mode) }
    val state by viewModel.subscribe { event ->
        when (event) {
            is OverviewEvent.Exit -> {
                if (mode == OverviewMode.FromSite) {
                    destinationsNavigator.navigate(SiteDestination)
                } else {
                    destinationsNavigator.navigate(HomeDestination)
                }
            }
        }
    }
    OverviewScreen(state = state, intent = viewModel::intent)
}
