# Simon Dice en Kotlin con MVVM

Esta aplicación de "Simon Dice" en Kotlin implementa el patrón de arquitectura MVVM y utiliza la biblioteca Compose para la creación de interfaces de usuario en Android. A continuación, se presenta una explicación detallada de cada clase y sus funciones.

## Clase Data

### Descripción
Esta clase contiene los datos del juego, como la ronda actual, la secuencia generada, la secuencia de entrada del usuario, el récord y el estado actual del juego.

<details>
<summary>Detalles: atributos y enumeraciones</summary>

### Atributos
```kotlin
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
    // ...
}
```

- `round`: MutableState que almacena la ronda actual del juego.
- `sequence`: MutableList que guarda la secuencia generada de colores.
- `inputSequence`: MutableList que guarda la secuencia de entrada del usuario.
- `record`: MutableState que almacena el récord actual.
- `state`: Enumeración que representa el estado actual del juego (START, SEQUENCE, WAITING, CHECKING, FINISHED).
- `colours`: Lista de colores disponibles para el juego.

### Enumeraciones
```kotlin
enum class State {
    START,
    SEQUENCE,
    WAITING,
    CHECKING,
    FINISHED
}

enum class Colours(val colour: MutableState<Color>, val colorName: String) {
    MAGENTA(mutableStateOf(Color.Magenta), "PINK"),
    CYAN(colour = mutableStateOf(Color.Cyan), "BLUE"),
    YELLOW(colour = mutableStateOf(Color.Yellow), "YELLOW"),
    GREEN(colour = mutableStateOf(Color.Green), "GREEN"),
    // ...
}
```

- `State`: Define los posibles estados del juego.
- `Colours`: Enumera los colores disponibles en el juego con sus representaciones de color y nombres.

</details>

## Clase MainActivity

### Descripción
Esta clase representa la actividad principal de la aplicación Android y configura la interfaz de usuario mediante Compose.

<details>
<summary>Detalles: métodos</summary>

### Métodos
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonSays {
                Surface {
                    Greeting(myModel = VModel())
                }
            }
        }
    }
}
```

- `onCreate(savedInstanceState: Bundle?)`: Método que se ejecuta al crear la actividad. Configura la interfaz de usuario utilizando Compose.

</details>

## Clase UI

### Descripción
Contiene funciones y componentes Compose para la interfaz de usuario.

<details>
<summary>Detalles: funciones principales</summary>

### Funciones Principales
```kotlin
@Composable
fun Greeting(myModel: VModel) {
    // ...
}

@Composable
fun  Record() {
    // ...
}

@Composable
fun Ronda() {
    // ...
}

@Composable
fun ButtonPanel(vModel: VModel) {
    // ...
}

@Composable
fun Boton(color: MutableState<Color>, myModel: VModel, name: String) {
    // ...
}

@Composable
fun StartButton(myModel: VModel) {
    // ...
}

@Composable
fun Send(myModel: VModel) {
    // ...
}
```

- `Greeting(myModel: VModel)`: Composición de la interfaz de usuario, que muestra la información de la ronda y el récord, los botones de colores y los botones de inicio y envío.
- `Record()`: Muestra el récord actual.
- `Ronda()`: Muestra la ronda actual.
- `ButtonPanel(vModel: VModel)`: Genera los botones de colores en dos filas para la interfaz de usuario.
- `Boton(color: MutableState<Color>, myModel: VModel, name: String)`: Define un botón de color con un nombre asociado.
- `StartButton(myModel: VModel)`: Define el botón de inicio del juego.
- `Send(myModel: VModel)`: Define el botón de envío para que el usuario verifique su secuencia de colores.

</details>

## Clase VModel

### Descripción
Esta clase ViewModel gestiona la lógica del juego y la comunicación entre la interfaz de usuario y los datos.

<details>
<summary>Detalles: métodos principales</summary>

### Métodos Principales
```kotlin
class VModel : ViewModel() {
    // ...
}
```

- `startGame()`: Inicia el juego estableciendo los valores iniciales.
- `changeState()`: Cambia el estado del juego.
- `pressButtonChangeColour(colour: MutableState<Color>)`: Cambia el color de un botón cuando se presiona.
- `createSequence()`: Genera una nueva secuencia de colores y muestra visualmente la secuencia al jugador.
- `increaseSequence()`: Incrementa la ronda del juego y genera una nueva secuencia.
- `storeInputSequence(color: Int)`: Almacena la entrada del usuario en la secuencia.
- `checkSequence(): Boolean`: Verifica si la secuencia del usuario coincide con la generada.

</details>

<p>
<p>
Estos son solo algunos de los aspectos más destacados de la implementación. Para obtener detalles más específicos, revisa el código fuente en cada archivo correspondiente. ¡Disfruta jugando Simon Dice!
