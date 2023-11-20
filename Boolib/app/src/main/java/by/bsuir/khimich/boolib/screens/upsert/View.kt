package by.bsuir.khimich.boolib.screens.upsert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.models.UpsertState
import by.bsuir.khimich.boolib.screens.LoadingScreen
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import java.util.*

@Composable
fun UpsertScreen(
    onSave: ((Book?) -> Unit)? = null,
    onDelete: ((UUID?) -> Unit)? = null,
    upsertState: UpsertState? = null,
) {
    when (upsertState) {
        is UpsertState.Loading -> {
            LoadingScreen()
        }

        is UpsertState.DisplayingBook -> {

            val book = upsertState.book ?: Book.getNotFoundBook()
            UpsertScreenContent(onSave = onSave, onDelete = onDelete, startBook = book)
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertScreenContent(
    onSave: ((Book) -> Unit)? = null,
    onDelete: ((UUID?) -> Unit)? = null,
    startBook: Book? = null,
) {
    var bookName by remember { mutableStateOf(startBook?.name ?: "ERROR") }
    var numberText by remember { mutableStateOf(startBook?.lastPaper.toString()) }
    var authorsText by remember { mutableStateOf(startBook?.authors?.joinToString("\n") ?: "ERROR") }
    val checked = remember { mutableStateOf(startBook?.isRead ?: false) }

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
                        "Edit or Add",
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
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        modifier = Modifier
                            .width(200.dp)
                            .height(70.dp)
                            .padding(6.dp),
                        onClick = { onDelete?.invoke(startBook?.id) }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            textAlign = TextAlign.Center,
                            text = "Delete",
                        )
                    }
                    Button(
                        modifier = Modifier
                            .width(200.dp)
                            .height(70.dp)
                            .padding(6.dp),
                        onClick = {
                            onSave?.invoke(
                                Book(
                                    name = bookName,
                                    isRead = checked.value,
                                    lastPaper = numberText.toInt(),
                                    authors = listOf(authorsText),
                                    id = startBook?.id ?: UUID.randomUUID()
                                )
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            textAlign = TextAlign.Center,
                            text = "Save",
                        )
                    }
                }
            }
        }
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
                    value = bookName,
                    onValueChange = { value ->
                        if (value.length <= 25) {
                            bookName = value
                        }
                    },
                    label = { Text("Book name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun UpsertScreenPreview() {
    BoolibTheme {
        UpsertScreen(upsertState = UpsertState.DisplayingBook(null))
    }
}
