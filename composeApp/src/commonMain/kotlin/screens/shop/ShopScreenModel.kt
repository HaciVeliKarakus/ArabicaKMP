package screens.shop

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class ShopScreenModel : ScreenModel {

    val state: StateFlow<AboutUiState> = flow {
        emit(AboutUiState(isLoading = true))
        val content = fetchAbout()
        emit(AboutUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), AboutUiState())

    private suspend fun fetchAbout(): String {
        val url = "https://arabicacoffee.com.tr/hakkimizda"
        val doc = Ksoup.parseGetRequest(url)
        return doc.select("div.page-bread").text()
    }
}

data class AboutUiState(
    val isLoading: Boolean = true,
    val content: String = ""
)