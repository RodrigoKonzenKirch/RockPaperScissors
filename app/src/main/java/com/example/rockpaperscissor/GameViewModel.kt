package com.example.rockpaperscissor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(): ViewModel() {

    private val _state = MutableStateFlow(GameScreenState())
    val state: StateFlow<GameScreenState> = _state.asStateFlow()

    fun onPlayerChoice(choice: HandChoice) {
        _state.value = _state.value.copy(
            playerHandChoice = choice
        )
    }

    fun onRestartGame() {
        _state.value = GameScreenState()
    }

    fun onPlay(){
        val randomChoice = (0..2).random()
        val computerHandChoice = when(randomChoice){
            0 -> HandChoice.Rock
            1 -> HandChoice.Paper
            else -> HandChoice.Scissors
        }

        updateLastRoundResult(computerHandChoice, state.value.playerHandChoice)
        compareHands(computerHandChoice, state.value.playerHandChoice)

    }

    private fun updateLastRoundResult(computerHandChoice: HandChoice, playerHandChoice: HandChoice){
        _state.value = _state.value.copy(
            lastRoundComputerHandChoice = computerHandChoice,
            lastRoundPlayerHandChoice = playerHandChoice
        )
    }

    private fun compareHands(computerHandChoice: HandChoice, playerHandChoice: HandChoice){

        if(computerHandChoice == playerHandChoice){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Draw,
            )
        } else if(computerHandChoice == HandChoice.Rock && playerHandChoice == HandChoice.Scissors){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Lose,
                computerScore = _state.value.computerScore + 1,
            )
        } else if(computerHandChoice == HandChoice.Rock && playerHandChoice == HandChoice.Paper){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Win,
                playerScore = _state.value.playerScore + 1,
            )
        } else if(computerHandChoice == HandChoice.Paper && playerHandChoice == HandChoice.Rock){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Lose,
                computerScore = _state.value.computerScore + 1,
            )
        } else if(computerHandChoice == HandChoice.Paper && playerHandChoice == HandChoice.Scissors){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Win,
                playerScore = _state.value.playerScore + 1,
            )
        } else if(computerHandChoice == HandChoice.Scissors && playerHandChoice == HandChoice.Paper){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Lose,
                computerScore = _state.value.computerScore + 1,
            )
        } else if(computerHandChoice == HandChoice.Scissors && playerHandChoice == HandChoice.Rock){
            _state.value = _state.value.copy(
                lastRoundResult = RoundResult.Win,
                playerScore = _state.value.playerScore + 1,
            )
        }
    }
}