package by.bsuir.khimich.boolib.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsuir.khimich.boolib.models.Book
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme

@Composable
fun SiteBookCard(book: Book, onClick: (Book) -> Unit) {
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
                Text(modifier = Modifier.fillMaxWidth(), text = "Title: " + book.name)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (book.isRead) "Read" else "Not read"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Last paper: " + book.lastPaper.toString()
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Authors: " + book.authors.joinToString(", ")
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(60.dp)
                        .height(60.dp),
                    onClick = { onClick(book) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SiteBookCardPreview() {
    BoolibTheme { SiteBookCard(Book.getNotFoundBook()) { } }
}
