package by.bsuir.khimich.boolib.screens.site

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import by.bsuir.khimich.boolib.models.SiteState
import by.bsuir.khimich.boolib.screens.LoadingScreen
import by.bsuir.khimich.boolib.screens.SiteBookCard
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import java.util.*

@Composable
fun SiteScreen(
    siteState: SiteState,
    onBookClick: (Book) -> Unit,
    toHomeScreen: () -> Unit,
    toOverview: (UUID?) -> Unit,
) {
    when (siteState) {
        is SiteState.Loading -> {
            LoadingScreen()
        }

        is SiteState.DisplayingBooks -> {
            SiteScreenContent(
                books = siteState.books,
                onBookClick = onBookClick,
                toHomeScreen = toHomeScreen,
                toOverview = toOverview
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteScreenContent(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    toHomeScreen: () -> Unit,
    toOverview: (UUID?) -> Unit,
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
                        stringResource(id = R.string.main_title) + " - Books From Site",
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
                    onClick = { toHomeScreen.invoke() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = "To Home",
                    )
                }
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
                SiteBookCard(books[index], onBookClick, toOverview)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SiteScreenContentPreview() {
    BoolibTheme { SiteScreenContent(books = listOf(Book.getNotFoundBook(), Book.getNotFoundBook()), {}, {}, {}) }
}
