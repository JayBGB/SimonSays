package com.example.simondice

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VModel : ViewModel() {

    private val tag = "COROUTINE" // for debugging

    // Esta función se llama cuando se pulsa el botón de START
    fun startGame() {
        Log.d(tag, "GAME STARTING")
        Data.round.value = 0
        Data.sequence = mutableListOf()
        Data.inputSequence = mutableListOf()
        Data.state = Data.State.START
    }


    // La función changeState() cambia el estado de la aplicación
    fun changeState() {
        Log.d(tag, "CHANGE APP STATE")

        Data.state = when (Data.state) {
            Data.State.START -> Data.State.SEQUENCE
            Data.State.SEQUENCE -> Data.State.WAITING
            Data.State.WAITING -> Data.State.CHECKING
            Data.State.CHECKING -> Data.State.FINISHED
            Data.State.FINISHED -> Data.State.START

        }

        getState()
    }

    // La función getState() devuelve el estado de la aplicación
    private fun getState(): Data.State {
        Log.d(tag, "CURRENT STATE IS: ${Data.state}")
        return Data.state

    }

    // La función genRandomNumber() genera un número aleatorio entre 0 y 3
    private fun genRandomNumber(): Int {
        return (0..3).random()

    }

    // La función pressButtonChangeColour() cambia el color de un botón cuando se pulsa
    @SuppressLint("SuspiciousIndentation")
    fun pressButtonChangeColour(colour: MutableState<Color>) {
        Log.d(tag, "Changes button colour when pressed")
        viewModelScope.launch {
            Data.colourPath = colour.value
            colour.value = darkenButtons(Data.colourPath)
            delay(300)
            colour.value = Data.colourPath
        }
    }

    // La función darkenButtons() oscurece los botones
    private fun darkenButtons(colour: Color): Color {
        val r = (colour.red * (1 - 0.5f)).coerceIn(0f, 1f)
        val g = (colour.green * (1 -  0.5f)).coerceIn(0f, 1f)
        val b = (colour.blue * (1 -  0.5f)).coerceIn(0f, 1f)
        return Color(r, g, b, colour.alpha)
    }

    var buttonsEnabled by mutableStateOf(true) // buttons are enabled by default

    // La función disableButtons() deshabilita los botones
    private fun disableButtons() {
        buttonsEnabled = false
    }

    // La función enableButtons() habilita los botones
    private fun enableButtons() {
        buttonsEnabled = true
    }

    // La función createSequence() crea una secuencia aleatoria de colores
    fun createSequence() {
        Log.d(tag, "CREATE SEQUENCE")
        Log.d(tag, "CURRENT STATE: ${Data.state}")
        Data.sequence.add(genRandomNumber())
        Log.d(tag, "CREATED SEQUENCE: ${Data.sequence}")


        disableButtons() // disable buttons while showing the sequence

        // show the sequence
        viewModelScope.launch {

            for (i in Data.sequence) {
                Log.d(tag, "SHOW COLOUR $i")
                Data.colourPath = Data.colourNumber[i].colour.value
                Data.colourNumber[i].colour.value = darkenButtons(Data.colourPath)
                delay(400)
                Data.colourNumber[i].colour.value = Data.colourPath
                delay(400)
            }

            enableButtons()
        }
    }

    // La función increaseSequence() incrementa la secuencia
    fun increaseSequence(){
        if (Data.state == Data.State.SEQUENCE){
            createSequence()
            Data.state = Data.State.WAITING
            Log.d(tag, "CHANGE STATE TO ${Data.state}")

            Data.inputSequence = mutableListOf()

        }
    }


    // La función storeInputSequence() almacena la secuencia de entrada
    fun storeInputSequence(color: Int) {
        Data.inputSequence.add(color)
        Log.d(tag, "INPUT SEQUENCE: ${Data.inputSequence}")

    }

    // La función checkSequence() comprueba si la secuencia de entrada es correcta
    fun checkSequence(): Boolean {
        val correcta : Boolean
        Data.state = Data.State.CHECKING
        Log.d(tag, "CHANGE STATE TO ${Data.state}")
        if (Data.sequence == Data.inputSequence) {
            Log.d(tag, "OK")
            Data.state = Data.State.SEQUENCE
            correcta = true
            Data.round.value++

            if (Data.round.value > Data.record.value) {
                Data.record.value = Data.round.value
            }
        } else {
            Log.d(tag, "NOT OK")
            correcta = false
            Data.state = Data.State.FINISHED
            Log.d(tag, "CHANGE APP STATE TO ${Data.state}")
            Data.round.value = 0
        }
        return correcta
    }




}