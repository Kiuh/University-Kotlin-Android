package by.bsuir.khimich.boolib

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import by.bsuir.khimich.boolib.destinations.AboutDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator?
) {
    Button(onClick = { destinationsNavigator?.navigate(AboutDestination()) }) {
        Text(text = "Go to about")
    }
}