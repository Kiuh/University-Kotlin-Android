package by.bsuir.khimich.boolib.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import by.bsuir.khimich.boolib.models.HomeViewModel
import by.bsuir.khimich.boolib.models.OverviewMode
import by.bsuir.khimich.boolib.screens.destinations.AboutDestination
import by.bsuir.khimich.boolib.screens.destinations.OverviewDestination
import by.bsuir.khimich.boolib.screens.destinations.SiteDestination
import by.bsuir.khimich.boolib.screens.destinations.UpsertDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.compose.koinInject

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(destinationsNavigator: DestinationsNavigator) = HomeCover(destinationsNavigator = destinationsNavigator)

@Composable
fun HomeCover(destinationsNavigator: DestinationsNavigator) {
    val viewModel = koinInject<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    HomeScreen(
        homeState = state,
        removeBook = { id -> viewModel.onBookRemove(id) },
        onBookClick = { id -> destinationsNavigator.navigate(UpsertDestination(id)) },
        toAboutScreen = { destinationsNavigator.navigate(AboutDestination()) },
        toSiteScreen = { destinationsNavigator.navigate(SiteDestination()) },
        toOverviewScreen = { id -> destinationsNavigator.navigate(OverviewDestination(id, OverviewMode.FromLocal)) }
    )
}
