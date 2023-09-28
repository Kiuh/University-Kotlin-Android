package by.bsuir.khimich.boolib

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    toAboutScreen: (() -> Unit)?,
    books: List<Book>?,
    onRemove: ((Book) -> Unit)?,
    onRedact: ((Book) -> Unit)?,
    onAdd: (() -> Unit)?,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.about_title),
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
                    onClick = { toAboutScreen?.invoke() }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = "Go to About",
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { if (onAdd != null) onAdd() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
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
                    BookCard(books[index], onRemove, onRedact)
                }
            }
        }
    }
}

@Composable
fun BookCard(book: Book, onRemove: ((Book) -> Unit)?, onRedact: ((Book) -> Unit)?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.LightGray)
            .padding(5.dp),
    ) {
        Column {
            Row(modifier = Modifier.fillMaxHeight(0.5f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.3f),
                    text = book.name
                )
                Text(
                    modifier = Modifier.fillMaxWidth(0.3f),
                    text = book.isRead.toString()
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(2.dp),
                    onClick = { if (onRemove != null) onRemove(book) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Remove Book"
                    )
                }
            }
            Row(modifier = Modifier.fillMaxHeight()) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.2f),
                    text = book.lastPaper.toString()
                )
                Text(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    text = book.authors.joinToString(", ")
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(2.dp),
                    onClick = { if (onRedact != null) onRedact(book) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Redact"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BoolibTheme {
        HomeScreen(
            null, HomeViewModel().items, null, null, null
        )
    }
}