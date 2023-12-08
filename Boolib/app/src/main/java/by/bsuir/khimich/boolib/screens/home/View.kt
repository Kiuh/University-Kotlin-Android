package by.bsuir.khimich.boolib.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsuir.khimich.boolib.R
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.models.HomeState
import by.bsuir.khimich.boolib.screens.BookCard
import by.bsuir.khimich.boolib.screens.LoadingScreen
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import java.util.*

@Composable
fun HomeScreen(
    homeState: HomeState,
    removeBook: (UUID?) -> Unit,
    onBookClick: (UUID?) -> Unit,
    toAboutScreen: () -> Unit,
    toSiteScreen: () -> Unit,
    toOverviewScreen: (UUID?) -> Unit,
) {
    when (homeState) {
        is HomeState.Loading -> {
            LoadingScreen()
        }

        is HomeState.DisplayingBooks -> {
            HomeScreenContent(
                books = homeState.books,
                removeBook = removeBook,
                updateBook = { id -> onBookClick.invoke(id) },
                addBook = { onBookClick.invoke(null) },
                toAboutScreen = toAboutScreen,
                toSiteScreen = toSiteScreen,
                toOverviewScreen = toOverviewScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    books: List<Book>,
    removeBook: (UUID?) -> Unit,
    updateBook: (UUID?) -> Unit,
    addBook: () -> Unit,
    toAboutScreen: () -> Unit,
    toSiteScreen: () -> Unit,
    toOverviewScreen: (UUID?) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        stringResource(id = R.string.main_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(6.dp),
                    onClick = { toAboutScreen.invoke() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = stringResource(id = R.string.go_to_about),
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(6.dp),
                    onClick = { toSiteScreen.invoke() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = "Go to site",
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addBook.invoke()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier =
            Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .height(1000.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(books.count(), key = { books[it].id }) { index ->
                BookCard(books[index], removeBook, updateBook, toOverviewScreen)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    BoolibTheme {
        HomeScreenContent(
            books = listOf(Book.getNotFoundBook(), Book.getNotFoundBook()),
            {},
            {},
            {},
            {},
            {},
            {})
    }
}
