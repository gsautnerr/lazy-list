package br.com.gsautnerr.github.listalazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gsautnerr.github.listalazy.model.Game
import br.com.gsautnerr.github.listalazy.repository.getAllGames
import br.com.gsautnerr.github.listalazy.repository.getGamesByStudio
import br.com.gsautnerr.github.listalazy.ui.theme.ListaLazyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaLazyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GamesScreen()
                }
            }
        }
    }
}


@Composable
fun GamesScreen() {

    var searchTextState by remember {
        mutableStateOf("")
    }
    var gamesListState by remember {
        mutableStateOf(getAllGames())
    }


    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchTextState,
            onValueChange = {
                searchTextState = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome do estúdio")
            },
            trailingIcon = {
                IconButton(onClick = { gamesListState = getGamesByStudio(searchTextState) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow() {
            items(gamesListState) {
                StudioCard(game = it)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn() {
            items(gamesListState) {
                GameCard(game = it)
            }
        }

    }
}

@Composable
fun StudioCard(game: Game) {
    Card(modifier = Modifier
        .size(100.dp)
        .padding(end = 4.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = game.studio)
        }
    }
}
@Composable
fun GameCard(game: Game) {
    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(3f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = game.releaseYear.toString(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true, name = "Games Screen Preview")
@Composable
fun PreviewGamesScreen() {
    ListaLazyTheme {
        GamesScreen()
    }
}
@Preview(showBackground = true, name = "Studio Card Preview")
@Composable
fun PreviewStudioCard() {
    ListaLazyTheme {
        StudioCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}
@Preview(showBackground = true, name = "Game Card Preview")
@Composable
fun PreviewGameCard() {
    ListaLazyTheme {
        GameCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}

