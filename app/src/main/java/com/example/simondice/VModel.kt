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

    private val tag = "COROUTINE"

    fun startGame() {
        Log.d(tag, "GAME STARTING")
        Data.round.value = 0
        Data.sequence = mutableListOf()
        Data.inputSequence = mutableListOf()
        Data.state = Data.State.START
    }


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

    private fun getState(): Data.State {
        Log.d(tag, "CURRENT STATE IS: ${Data.state}")
        return Data.state

    }

    private fun genRandomNumber(): Int {
        return (0..3).random()

    }

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

    private fun darkenButtons(colour: Color): Color {
        val r = (colour.red * (1 - 0.5f)).coerceIn(0f, 1f)
        val g = (colour.green * (1 -  0.5f)).coerceIn(0f, 1f)
        val b = (colour.blue * (1 -  0.5f)).coerceIn(0f, 1f)
        return Color(r, g, b, colour.alpha)
    }

    var buttonsEnabled by mutableStateOf(true)

    private fun disableButtons() {
        buttonsEnabled = false
    }

    private fun enableButtons() {
        buttonsEnabled = true
    }

    fun createSequence() {
        Log.d(tag, "CREATE SEQUENCE")
        Log.d(tag, "CURRENT STATE: ${Data.state}")
        Data.sequence.add(genRandomNumber())
        Log.d(tag, "CREATED SEQUENCE: ${Data.sequence}")


        disableButtons()

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

    fun increaseSequence(){
        if (Data.state == Data.State.SEQUENCE){
            createSequence()
            Data.state = Data.State.WAITING
            Log.d(tag, "CHANGE STATE TO ${Data.state}")

            Data.inputSequence = mutableListOf()

        }
    }


    fun storeInputSequence(color: Int) {
        Data.inputSequence.add(color)
        Log.d(tag, "INPUT SEQUENCE: ${Data.inputSequence}")

    }

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