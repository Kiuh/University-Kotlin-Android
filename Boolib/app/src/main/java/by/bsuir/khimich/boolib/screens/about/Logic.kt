package by.bsuir.khimich.boolib.screens.about

import androidx.compose.runtime.Composable
import by.bsuir.khimich.boolib.screens.destinations.HomeDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun About(destinationsNavigator: DestinationsNavigator) =
    AboutScreenCover(destinationsNavigator = destinationsNavigator)

@Composable
fun AboutScreenCover(destinationsNavigator: DestinationsNavigator) {
    AboutScreen(toHomeScreen = { destinationsNavigator.navigate(HomeDestination()) })
}
