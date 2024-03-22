package screens.about

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AboutScreenModel : ScreenModel {
    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    init {
        fetchAbout()
    }

    private fun fetchAbout() {
        screenModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val url = "https://arabicacoffee.com.tr/hakkimizda"

            val doc = Ksoup.parseGetRequest(url)

            val content = doc.select("div.page-bread").text()
            _content.value = content
            _isLoading.value = false
        }

    }
}