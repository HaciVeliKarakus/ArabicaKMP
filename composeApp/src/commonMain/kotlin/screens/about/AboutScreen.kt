package screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import arabicakmp.composeapp.generated.resources.Res
import arabicakmp.composeapp.generated.resources.arabicanewlogo
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ArabicaLayout
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


object AboutScreen : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 4u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<AboutScreenModel>()
        val state by screenModel.state.collectAsState()

        contentUI(state)
    }
}

@Composable
private fun contentUI(state: AboutUiState) {
    ArabicaLayout(state.isLoading) {
        Text(state.content)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Logo() {
    Image(
        painterResource(Res.drawable.arabicanewlogo),
        contentDescription = "logo",
        modifier = Modifier.fillMaxWidth()
    )
}
