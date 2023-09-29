package by.bsuir.khimich.boolib.screens

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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.khimich.boolib.R
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.models.HomeViewModel
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class BottomSheetMode {
    Add,
    Change
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    toAboutScreen: (() -> Unit)? = null,
    books: List<Book>?,
    onRemove: ((Book) -> Unit)? = null,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetMode by remember { mutableStateOf(BottomSheetMode.Add) }

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
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
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
                    modifier = Modifier.fillMaxWidth(0.4f).padding(6.dp),
                    onClick = { toAboutScreen?.invoke() }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(2.dp),
                        text = stringResource(id = R.string.go_to_about),
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                    bottomSheetMode = BottomSheetMode.Add
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier.padding(paddingValues)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .height(1000.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (books != null) {
                items(books.count(), key = { books[it].id }) { index ->
                    BookCard(books[index], onRemove) { _ ->
                        showBottomSheet = true
                        bottomSheetMode = BottomSheetMode.Change
                    }
                }
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                setShowBottomSheet = { value -> showBottomSheet = value },
                sheetState = sheetState,
                scope = scope,
                mode = bottomSheetMode
            )
        }
    }
}

@Composable
fun BookCard(book: Book, onRemove: ((Book) -> Unit)?, onRedact: ((Book) -> Unit)?) {
    Box(
        modifier =
            Modifier.fillMaxWidth()
                .height(120.dp)
                .background(color = Color.LightGray)
                .padding(5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(modifier = Modifier, text = "Title: " + book.name)
                Text(modifier = Modifier, text = if (book.isRead) "Read" else "Not read")
                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { if (onRemove != null) onRemove(book) }
                ) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(modifier = Modifier, text = "Last paper: " + book.lastPaper.toString())
                Text(modifier = Modifier, text = "Authors: " + book.authors.joinToString(", "))
                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { if (onRedact != null) onRedact(book) }
                ) {
                    Icon(Icons.Default.Build, contentDescription = null)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    setShowBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
    mode: BottomSheetMode,
) {
    ModalBottomSheet(
        onDismissRequest = { setShowBottomSheet(false) },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        BottomSheetContent(
            sheetState = sheetState,
            scope = scope,
            setShowBottomSheet = setShowBottomSheet,
            mode = mode
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    sheetState: SheetState,
    scope: CoroutineScope,
    setShowBottomSheet: (Boolean) -> Unit,
    mode: BottomSheetMode,
) {
    var text by remember { mutableStateOf("") }
    var numberText by remember { mutableStateOf("") }
    var authorsText by remember { mutableStateOf("") }

    val checked = remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { value ->
                if (value.length <= 25) {
                    text = value
                }
            },
            label = { Text("Book name") },
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        )
        OutlinedTextField(
            maxLines = 1,
            value = numberText,
            onValueChange = { value ->
                if (value.length <= 7) {
                    numberText = value.filter { it.isDigit() }
                }
            },
            label = { Text("Last read paper") },
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { newChecked -> checked.value = newChecked },
                modifier = Modifier.padding(5.dp)
            )
            Text("I has read this book", fontSize = 22.sp)
        }
        OutlinedTextField(
            maxLines = 1,
            value = authorsText,
            onValueChange = { value -> authorsText = value },
            label = { Text("Authors of book") },
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                scope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            setShowBottomSheet(false)
                        }
                    }
            }
        ) {
            Text(text = if (mode == BottomSheetMode.Change) "Change content" else "Add new Book")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun BottomSheetContentPreview() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    BoolibTheme {
        BottomSheetContent(
            sheetState = sheetState,
            scope = scope,
            setShowBottomSheet = { _ -> },
            mode = BottomSheetMode.Change
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BoolibTheme { HomeScreen(books = HomeViewModel().items) }
}
