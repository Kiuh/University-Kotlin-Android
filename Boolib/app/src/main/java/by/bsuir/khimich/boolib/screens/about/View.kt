package by.bsuir.khimich.boolib.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsuir.khimich.boolib.R
import by.bsuir.khimich.boolib.ui.theme.BoolibTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(toHomeScreen: () -> Unit) {
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
                    onClick = { toHomeScreen.invoke() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = stringResource(id = R.string.back_from_about),
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier =
            Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.about_app_name))
            Text(text = stringResource(id = R.string.app_version))
            Text(text = stringResource(id = R.string.about_description))
            Text(text = stringResource(id = R.string.about_author_title))
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(200.dp),
                painter = painterResource(id = R.drawable.author_image),
                contentDescription = null
            )
            Text(text = stringResource(id = R.string.author_name))
            for (text in stringArrayResource(id = R.array.author_info)) {
                Text(color = Color.Red, text = text)
            }
            Text(text = stringResource(id = R.string.facts_title))
            for (text in stringArrayResource(id = R.array.facts_about_author)) {
                Text(color = Color.Red, text = text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    BoolibTheme { AboutScreen {} }
}
