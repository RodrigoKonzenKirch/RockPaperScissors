package com.example.rockpaperscissor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissor.ui.theme.RockPaperScissorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RockPaperScissorTheme {
                val viewModel = GameViewModel()
                val state = viewModel.state.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = "Player ${state.value.playerScore} X ${state.value.computerScore} Computer")

                                    Button(onClick = viewModel::onRestartGame ) {
                                        Text(text = "Restart Game")
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        PlayerChoiceButtons(
                            playerSelection = state.value.playerHandChoice,
                            onChooseHand = {
                                viewModel.onPlayerChoice(it)
                            }
                        )
                    }
                ) { innerPadding ->
                    GameScreen(
                        modifier = Modifier.padding(innerPadding),
                        lastRoundResult = state.value.lastRoundResult,
                        lastRoundPlayerHandChoice = state.value.lastRoundPlayerHandChoice,
                        lastRoundComputerHandChoice = state.value.lastRoundComputerHandChoice,
                        onPlay = viewModel::onPlay
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    lastRoundResult: RoundResult,
    lastRoundPlayerHandChoice: HandChoice,
    lastRoundComputerHandChoice: HandChoice,
    onPlay: () -> Unit = {}
) {
    val playerBackgroundColor = Color(red = 144, green = 238, blue = 144)
    val computerBackgroundColor = Color(red = 238, green = 144, blue = 238)
    val imageSize = 180.dp
    val cardSize = 200.dp
    val lastRoundResultMessage = when (lastRoundResult) {
        RoundResult.Draw -> "Draw"
        RoundResult.Win -> "Player Win"
        RoundResult.Lose -> "Computer Win"
        RoundResult.NotPlayed -> "Not Played Yet"
    }
    val lastRoundPlayerHandChoiceImage = when (lastRoundPlayerHandChoice) {
        HandChoice.Rock -> R.drawable.rock
        HandChoice.Paper -> R.drawable.paper
        HandChoice.Scissors -> R.drawable.scissor
    }
    val lastRoundComputerHandChoiceImage = when (lastRoundComputerHandChoice) {
        HandChoice.Rock -> R.drawable.rock
        HandChoice.Paper -> R.drawable.paper
        HandChoice.Scissors -> R.drawable.scissor
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxSize().padding(16.dp)) {

        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.align(Alignment.CenterHorizontally).requiredSize(cardSize),
            colors = CardDefaults.cardColors(
                containerColor = computerBackgroundColor
            )
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Computer")
            Image(
                painter = painterResource(id = lastRoundComputerHandChoiceImage),
                contentDescription = "Computer",
                modifier = Modifier.align(Alignment.CenterHorizontally).size(imageSize)
            )
        }

        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.align(Alignment.CenterHorizontally).requiredSize(cardSize),
            colors = CardDefaults.cardColors(
                containerColor = playerBackgroundColor
            )
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Player")
            Image(
                painter = painterResource(id = lastRoundPlayerHandChoiceImage),
                contentDescription = "Player",
                modifier = Modifier.align(Alignment.CenterHorizontally).size(imageSize)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onPlay() },
                shape = MaterialTheme.shapes.extraSmall,
            ) { Text("Play!") }
            Text(text = "Last round: $lastRoundResultMessage")
        }
    }
}

@Composable
fun PlayerChoiceButtons(
    modifier: Modifier = Modifier,
    playerSelection: HandChoice,
    onChooseHand: (HandChoice) -> Unit = {}

) {
    val colorUnselected = Color(red = 0, green = 122, blue = 255)
    val colorSelected = Color(red = 255, green = 165, blue = 0)

    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onChooseHand(HandChoice.Rock)
            },
            modifier = Modifier.weight(if (playerSelection == HandChoice.Rock) 1.2f else 1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (playerSelection == HandChoice.Rock) colorSelected else colorUnselected
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.rock),
                contentDescription = "Rock",
            )
        }

        Button(
            onClick = {
                onChooseHand(HandChoice.Paper)
            },
            modifier = Modifier.weight(if (playerSelection == HandChoice.Paper) 1.2f else 1f),
            colors = ButtonDefaults.buttonColors(
                if (playerSelection == HandChoice.Paper) colorSelected else colorUnselected
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.paper),
                contentDescription = "Paper",
            )
        }

        Button(
            onClick = {
                onChooseHand(HandChoice.Scissors)
            },
            modifier = Modifier.weight(if (playerSelection == HandChoice.Scissors) 1.2f else 1f),
            colors = ButtonDefaults.buttonColors(
                if (playerSelection == HandChoice.Scissors) colorSelected else colorUnselected
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.scissor),
                contentDescription = "Scissor",
            )
        }
    }
}

