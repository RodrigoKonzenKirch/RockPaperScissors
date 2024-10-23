package com.example.rockpaperscissor

data class GameScreenState(
    val playerScore: Int = 0,
    var playerHandChoice: HandChoice = HandChoice.Rock,
    val computerScore: Int = 0,
    val lastRoundPlayerHandChoice: HandChoice = HandChoice.Rock,
    val lastRoundComputerHandChoice: HandChoice = HandChoice.Rock,
    val lastRoundResult: RoundResult = RoundResult.NotPlayed,

    )