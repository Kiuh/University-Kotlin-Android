package by.bsuir.khimich.boolib.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Loading...")
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    BoolibTheme { LoadingScreen() }
}
