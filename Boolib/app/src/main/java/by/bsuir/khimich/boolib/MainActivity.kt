package by.bsuir.khimich.boolib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.khimich.boolib.destinations.AboutDestination
import by.bsuir.khimich.boolib.destinations.HomeDestination
import by.bsuir.khimich.boolib.models.HomeViewModel
import by.bsuir.khimich.boolib.screens.AboutScreen
import by.bsuir.khimich.boolib.screens.HomeScreen
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BoolibTheme { DestinationsNavHost(navGraph = NavGraphs.root) } }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(destinationsNavigator: DestinationsNavigator) {
    val viewModel = viewModel<HomeViewModel>()
    HomeScreen(
        toAboutScreen = { destinationsNavigator.navigate(AboutDestination()) },
        books = viewModel.items,
        onRemove = viewModel::onClickRemoveBook
    )
}

@Destination
@Composable
fun About(destinationsNavigator: DestinationsNavigator) {
    AboutScreen(toHomeScreen = { destinationsNavigator.navigate(HomeDestination()) })
}
