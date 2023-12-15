package com.example.simondice

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable

// La función Greeting() es la función principal de la aplicación
fun Greeting(myModel: VModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Ronda()
            Spacer(modifier = Modifier.width(16.dp))
            Record()

        }

        ButtonPanel(vModel = myModel)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            StartButton(myModel = myModel)
            Spacer(modifier = Modifier.width(16.dp))
            Send(myModel = myModel)
        }
    }
}

// La función Record() muestra el record de la partida
@Composable
fun  Record(){
    Text(
        text = "RECORD: ${Data.record.value} ",
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    )
}

// La función Ronda() muestra la ronda actual
@Composable
fun Ronda() {
    Text(
        text = "ROUND: ${Data.round.value} ",
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    )

}

// La función ButtonPanel() muestra los botones de la aplicación
@Composable
fun ButtonPanel(vModel: VModel) {
    val coloursInTwoRows = Data.Colours.values().toList().chunked(2)

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        coloursInTwoRows.forEach { rowColors ->
            Row {
                rowColors.forEach { color ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Boton(color = color.colour, myModel = vModel, name = color.colorName)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// La función Boton() muestra los botones de la aplicación
@Composable
fun Boton(color: MutableState<Color>, myModel: VModel, name: String) {

    Button(
        onClick = {

            if (Data.state == Data.State.WAITING && myModel.buttonsEnabled) {
                myModel.storeInputSequence(Data.colours.indexOf(color))
                myModel.pressButtonChangeColour(color)
            }
        },
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp)
            .padding(8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),

        colors = ButtonDefaults.buttonColors(color.value)
    ) {
        Text(
            text = name,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}

// La función StartButton() muestra el botón de START
@Composable
fun StartButton(myModel: VModel) {
    Button(
        onClick = {
            myModel.startGame()
            myModel.changeState()
            if (Data.state == Data.State.SEQUENCE ){
                myModel.createSequence()
                myModel.changeState()
            }else{
                myModel.startGame()
            }

        },
        modifier = Modifier

    ) {
        Text(
            text = "START",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}
// La función Send() muestra el botón de SEND
@Composable
fun Send(myModel: VModel) {
    Button(
        onClick = {
            if (Data.state == Data.State.WAITING){
                if ( myModel.checkSequence()) {
                    Log.d("COROUTINE", "CORRECT SEQUENCE, WE RUN INCREASE SEQUENCE")
                    myModel.increaseSequence()
                }else{
                    Log.d("COROUTINE", "INCORRECT SEQUENCE")
                    Data.state = Data.State.FINISHED
                }

            }


        },
        modifier = Modifier
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = "SEND",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}