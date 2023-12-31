package com.example.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

// La clase Data contiene los datos de la aplicación
object Data {
    var round = mutableStateOf(0);
    var sequence = mutableListOf<Int>();
    var inputSequence = mutableListOf<Int>();
    var record = mutableStateOf(0);
    var state = State.START;
    var colours = listOf(
        Colours.MAGENTA.colour,
        Colours.CYAN.colour,
        Colours.YELLOW.colour,
        Colours.GREEN.colour
    )



    var colourNumber = Colours.values() // Array de colores
    var colourPath: Color = Color.White // Color del botón

    // State representa el estado de la aplicación
    enum class State {
        START,
        SEQUENCE,
        WAITING,
        CHECKING,
        FINISHED
    }

    // Colours representa los colores de los botones
    enum class Colours(val colour: MutableState<Color>, val colorName: String) {
        MAGENTA(mutableStateOf(Color.Magenta), "PINK"),
        CYAN(colour = mutableStateOf(Color.Cyan), "BLUE"),
        YELLOW(colour = mutableStateOf(Color.Yellow), "YELLOW"),
        GREEN(colour = mutableStateOf(Color.Green), "GREEN"),

    }


}