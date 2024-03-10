package screens.about

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


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

            val doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(55_000)
                .get()
            val content = doc.select("div.page-bread").text()
            _content.value = content
            _isLoading.value = false
        }

    }
}