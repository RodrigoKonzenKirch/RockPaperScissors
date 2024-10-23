package com.example.rockpaperscissor

sealed class HandChoice(
){
    object Rock : HandChoice()
    object Paper : HandChoice()
    object Scissors : HandChoice()
}