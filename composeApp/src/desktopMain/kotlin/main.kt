import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState: WindowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ArabicaKMP"
    ) {
        App()
    }
}