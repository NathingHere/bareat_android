package com.example.data

sealed class MainState {
    object Empty : MainState()
    //data class DrawItems(val items: List<Objeto>) : MainState()
    data class ShowErrorMessage(val message: String) : MainState()
}

