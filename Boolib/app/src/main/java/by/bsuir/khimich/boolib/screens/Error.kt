package by.bsuir.khimich.boolib.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(text: String, back: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "ERROR",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text + "\n")
            Button(onClick = { back() }) {
                Text(text = "Fuck, Go Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    BoolibTheme { ErrorScreen("An Error occurred") {} }
}
