package by.bsuir.khimich.boolib.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsuir.khimich.boolib.R
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.models.HomeState
import by.bsuir.khimich.boolib.screens.LoadingScreen
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import java.util.*

@Composable
fun HomeScreen(
    homeState: HomeState? = null,
    removeBook: ((UUID?) -> Unit)? = null,
    onBookClick: ((UUID?) -> Unit)? = null,
    toAboutScreen: (() -> Unit)? = null,
) {
    when (homeState) {
        is HomeState.Loading -> {
            LoadingScreen()
        }

        is HomeState.DisplayingBooks -> {
            HomeScreenContent(
                books = homeState.books,
                removeBook = removeBook,
                updateBook = { id -> onBookClick?.invoke(id) },
                addBook = { onBookClick?.invoke(null) },
                toAboutScreen = toAboutScreen
            )
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    books: List<Book>? = null,
    removeBook: ((UUID?) -> Unit)? = null,
    updateBook: ((UUID?) -> Unit)? = null,
    addBook: (() -> Unit)? = null,
    toAboutScreen: (() -> Unit)? = null,
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
                    onClick = { toAboutScreen?.invoke() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = stringResource(id = R.string.go_to_about),
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addBook?.invoke()
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
            if (books != null) {
                items(books.count(), key = { books[it].id }) { index ->
                    BookCard(books[index], removeBook, updateBook)
                }
            }
        }
    }
}

@Composable
fun BookCard(book: Book? = null, onRemove: ((UUID?) -> Unit)? = null, onRedact: ((UUID?) -> Unit)? = null) {
    val showedBook = book ?: Book.getNotFoundBook()
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(color = Color.LightGray)
            .padding(5.dp),
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(modifier = Modifier.fillMaxWidth(), text = "Title: " + showedBook.name)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (showedBook.isRead) "Read" else "Not read"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Last paper: " + showedBook.lastPaper.toString()
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Authors: " + showedBook.authors.joinToString(", ")
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(60.dp)
                        .height(60.dp),
                    onClick = { if (onRemove != null) onRemove(showedBook.id) }
                ) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                }
                FloatingActionButton(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(60.dp)
                        .height(60.dp),
                    onClick = { if (onRedact != null) onRedact(showedBook.id) }
                ) {
                    Icon(Icons.Default.Build, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookCardPreview() {
    BoolibTheme { BookCard() }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    BoolibTheme { HomeScreenContent(books = listOf(Book.getNotFoundBook(), Book.getNotFoundBook())) }
}
