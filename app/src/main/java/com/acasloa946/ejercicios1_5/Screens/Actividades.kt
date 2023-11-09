package com.dam23_24.composecatalogolayout.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var textButton by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
            textButton = "Cancelar"
        }
        else {
            textButton = "Cargar perfil"
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = textButton)
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
@Composable
fun Actividad2() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var textButton by rememberSaveable { mutableStateOf("") }
    var padding by rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Blue,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Blue,
                trackColor = Color.LightGray
            )
            textButton = "Cancelar"
            padding = 30
        }
        else {
            textButton = "Cargar perfil"
            padding = 0
        }
        Button(
            onClick = { showLoading = !showLoading },
            modifier = Modifier.padding(top = padding.dp)
        ) {
            Text(text = textButton)
        }
    }

}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)

*/

@Composable

fun Actividad3() {
    var progreso by rememberSaveable { mutableStateOf(0f) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(progress = progreso.toFloat())

        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                if (progreso<1.0f) {
                    progreso += 0.1f
                    println(progreso)
                }

            }) {
                Text(text = "Incrementar")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                if (progreso>0.1f) {
                    progreso -= 0.10f

                }

            }) {
                Text(text = "Reducir")
            }
        }
    }
}


/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@Preview (showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }
    var puntoAnadido by rememberSaveable { mutableStateOf(false) }

Column (
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
){
    TextField(
        onValueChange = {
            if (it.isNotBlank()) {
                if (it.last()==',' && !puntoAnadido) {
                        myVal = it.replace(',','.')
                        puntoAnadido = true
                }
                else if (puntoAnadido && (it.last()==',') || (it.last()=='.')) {
                    myVal = it.dropLast(1)
                }
                else{
                    myVal = it
                }
            }
            else {
                puntoAnadido = false
                myVal = it
            }
                        },
        value = myVal,
        label = { Text(text = "Importe") },
    )
}

}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
var puntoAnadido = false

/**
 * Función elevada que controla el comportamiento del OutlinedTextField
 * @param: string -> it del onValueChange (string que se esta escribiendo)
 * @return filtrador -> string que es la que se esta imprimiendo en el value del OutlinedTextField
 */
fun funcionElevada(string: String):String {
    //filtro el string para que solo se puedan escribir dígitos, comas y puntos.
    var filtrador = string.filter { it.isDigit() || it == ',' || it == '.' }
    //si la string no está vacía
    if (filtrador.isNotBlank()) {
        //si el ultimo dígito es una coma y no se ha añadido un punto anteriormente.
        if (filtrador.last() == ',' && !puntoAnadido) {
            filtrador = filtrador.replace(',', '.')
            puntoAnadido = true
        }
        //si el último dígito es una coma o un punto y el punto ya ha sido añadido
        else if((filtrador.last()=='.' || filtrador.last()==',') && puntoAnadido) {
            filtrador = filtrador.dropLast(1)
        }
        //si no contiene ningun punto significa que el punto no ha sido añadido
        if (filtrador.contains('.')==false) {
            puntoAnadido=false
        }
    }
    //si la string esta vacía el punto no puede estar añadido.
    else {
        puntoAnadido = false
    }

     return filtrador
}

/**
 * Función de la actividad 5
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }
    var final by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(15.dp),
            onValueChange = { myVal = it 
                            final = funcionElevada(myVal)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            ),
            value = final,
            label = { Text(text = "Importe") }
        )
        Button(
            onClick = {  }) {
            Text(text = "Quitar foco")
        }
    }
}
