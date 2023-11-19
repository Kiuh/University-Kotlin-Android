package by.bsuir.khimich.boolib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import by.bsuir.khimich.boolib.screens.NavGraphs
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BoolibTheme { DestinationsNavHost(navGraph = NavGraphs.root) } }
    }
}
