package by.bsuir.khimich.boolib.screens.site

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import by.bsuir.khimich.boolib.models.SiteViewModel
import by.bsuir.khimich.boolib.screens.destinations.HomeDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.compose.koinInject

@Destination
@Composable
fun Site(destinationsNavigator: DestinationsNavigator) = SiteCover(destinationsNavigator = destinationsNavigator)

@Composable
fun SiteCover(destinationsNavigator: DestinationsNavigator) {
    val viewModel = koinInject<SiteViewModel>()
    val state by viewModel.state.collectAsState()

    SiteScreen(
        siteState = state,
        onBookClick = { book -> viewModel.onBookClick(book) },
        toHomeScreen = { destinationsNavigator.navigate(HomeDestination()) },
    )
}
