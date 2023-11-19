package by.bsuir.khimich.boolib.screens.upsert

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.khimich.boolib.models.UpsertState
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertScreen(
    onCallback: ((Boolean) -> Unit)? = null,
    upsertState: StateFlow<UpsertState>? = null,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onCallback?.invoke(false) },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {

        var text by remember { mutableStateOf("") }
        var numberText by remember { mutableStateOf("") }
        var authorsText by remember { mutableStateOf("") }
        val checked = remember { mutableStateOf(false) }

        val buttonText by remember { mutableStateOf("Change content") } // "Add new Book"

        Column {
            OutlinedTextField(
                value = text,
                onValueChange = { value ->
                    if (value.length <= 25) {
                        text = value
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
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onCallback?.invoke(false)
                            }
                        }
                }
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun UpsertScreenPreview() {
    BoolibTheme {
        UpsertScreen()
    }
}
