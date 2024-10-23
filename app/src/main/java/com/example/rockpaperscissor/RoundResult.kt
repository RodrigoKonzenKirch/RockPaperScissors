package com.example.rockpaperscissor

sealed class RoundResult {
    object NotPlayed : RoundResult()
    object Win : RoundResult()
    object Draw : RoundResult()
    object Lose : RoundResult()
}