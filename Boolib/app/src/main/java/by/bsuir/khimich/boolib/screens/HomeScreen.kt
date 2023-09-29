package by.bsuir.khimich.boolib.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
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
            stringResource(id = R.string.about_title),
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
            text = "Go to About",
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
        scope = scope
      )
    }
  }
}

@Composable
fun BookCard(book: Book, onRemove: ((Book) -> Unit)?, onRedact: ((Book) -> Unit)?) {
  Box(
    modifier =
      Modifier.fillMaxWidth().height(100.dp).background(color = Color.LightGray).padding(5.dp),
  ) {
    Column {
      Row(modifier = Modifier.fillMaxHeight(0.5f)) {
        Text(modifier = Modifier.fillMaxWidth(0.3f), text = book.name)
        Text(modifier = Modifier.fillMaxWidth(0.3f), text = book.isRead.toString())
        Button(
          modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(2.dp),
          onClick = { if (onRemove != null) onRemove(book) }
        ) {
          Text(modifier = Modifier.fillMaxWidth(), text = "Remove Book")
        }
      }
      Row(modifier = Modifier.fillMaxHeight()) {
        Text(modifier = Modifier.fillMaxWidth(0.2f), text = book.lastPaper.toString())
        Text(modifier = Modifier.fillMaxWidth(0.4f), text = book.authors.joinToString(", "))
        Button(
          modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(2.dp),
          onClick = { if (onRedact != null) onRedact(book) }
        ) {
          Text(modifier = Modifier.fillMaxWidth(), text = "Redact")
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
  scope: CoroutineScope
) {
  var text by remember { mutableStateOf("") }
  var numberText by remember { mutableStateOf("") }
  var authorsText by remember { mutableStateOf("") }

  val checked = remember { mutableStateOf(false) }

  ModalBottomSheet(
    onDismissRequest = { setShowBottomSheet(false) },
    sheetState = sheetState,
    modifier = Modifier.fillMaxHeight(0.7f)
  ) {
    // Sheet content
    Column {
      OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Book name") },
        modifier = Modifier.fillMaxWidth().padding(5.dp),
      )
      OutlinedTextField(
        maxLines = 1,
        value = numberText,
        onValueChange = { value ->
          if (value.length <= 2) {
            numberText = value.filter { it.isDigit() }
          }
        },
        label = { Text("Last read paper") },
        modifier = Modifier.fillMaxWidth().padding(5.dp),
      )
      Row(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Text("I has read this book")
        Checkbox(
          checked = checked.value,
          onCheckedChange = { newChecked -> checked.value = newChecked }
        )
      }
      OutlinedTextField(
        maxLines = 1,
        value = authorsText,
        onValueChange = { value -> authorsText = value },
        label = { Text("Authors of book") },
        modifier = Modifier.fillMaxWidth().padding(5.dp),
      )
      Button(
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
        Text("Hide bottom sheet")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  BoolibTheme { HomeScreen(books = HomeViewModel().items) }
}
