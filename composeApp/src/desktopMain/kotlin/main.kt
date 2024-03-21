import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin

fun main() = application {
    val windowState: WindowState = rememberWindowState()
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "ArabicaKMP"
    ) {
        App()
    }
}