package by.bsuir.khimich.boolib.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.models.OverviewIntent
import by.bsuir.khimich.boolib.models.OverviewState
import by.bsuir.khimich.boolib.screens.ErrorScreen
import by.bsuir.khimich.boolib.screens.LoadingScreen
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import java.util.*

@Composable
fun OverviewScreen(state: OverviewState, intent: (OverviewIntent) -> Unit) {
    when (state) {
        is OverviewState.Loading -> {
            LoadingScreen()
        }

        is OverviewState.DisplayingSiteBook -> {
            val book = state.book ?: Book.getNotFoundBook()
            OverviewScreenBookContent(book, state.inFavorites) {
                ButtonsContent(intent = intent, book.id)
            }
        }

        is OverviewState.DisplayingPrivateBook -> {
            val book = state.book ?: Book.getNotFoundBook()
            OverviewScreenBookContent(book, state.inFavorites) {
                ButtonsContent(intent = intent, book.id)
            }
        }

        is OverviewState.Error -> {
            ErrorScreen(text = state.text ?: "State text is null") {
                intent(OverviewIntent.ExitClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreenBookContent(book: Book, inFavorites: Boolean, bottomBar: @Composable () -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Book Overview",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            )
        },
        bottomBar = bottomBar
    ) { paddingValues ->
        Box(
            modifier =
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .fillMaxHeight(),
        ) {
            Column {
                OutlinedTextField(
                    value = book.name,
                    onValueChange = {
                    },
                    label = { Text("Book name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
                OutlinedTextField(
                    maxLines = 1,
                    value = book.lastPaper.toString(),
                    onValueChange = {
                    },
                    label = { Text("Last read paper") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = book.isRead,
                        onCheckedChange = { },
                        modifier = Modifier.padding(5.dp)
                    )
                    Text("I has read this book", fontSize = 22.sp)
                }
                OutlinedTextField(
                    maxLines = 1,
                    value = book.authors.joinToString("\n"),
                    onValueChange = { },
                    label = { Text("Authors of book") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = inFavorites,
                        onCheckedChange = { },
                        modifier = Modifier.padding(5.dp)
                    )
                    Text("Is in favorites", fontSize = 22.sp)
                }
            }
        }
    }
}

@Composable
fun ButtonsContent(intent: (OverviewIntent) -> Unit, id: UUID) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                modifier = Modifier
                    .width(110.dp)
                    .height(70.dp)
                    .padding(4.dp),
                onClick = {
                    intent(OverviewIntent.ExitClicked)
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                    text = "Exit",
                )
            }
            Button(
                modifier = Modifier
                    .width(130.dp)
                    .height(70.dp)
                    .padding(4.dp),
                onClick = {
                    intent(OverviewIntent.AddToFavoritesClicked(id))
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                    text = "Add To Favorites",
                )
            }
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(4.dp),
                onClick = {
                    intent(OverviewIntent.RemoveFromFavoritesClicked(id))
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                    text = "Remove from Favorites",
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun OverviewScreenPreview() {
    BoolibTheme {
        OverviewScreen(state = OverviewState.DisplayingPrivateBook(null, true)) { }
    }
}
