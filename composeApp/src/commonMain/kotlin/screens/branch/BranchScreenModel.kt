package screens.branch

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class BranchScreenModel : ScreenModel {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _branches = MutableStateFlow<List<Branch>>(listOf())
    val branches = _branches.asStateFlow()


    init {
        fetchBranches()
    }

    private fun fetchBranches() {
        val tmpBranches = mutableListOf<Branch>()
        screenModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val url = "https://arabicacoffee.com.tr/subeler"

            val doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(55_000)
                .get()
            val content = doc.select("div.branch-box")
            content.forEach {
                val imgUrl =
                    it.select("div.over_img").attr("style")
                        .split("url(")[1].substringBefore(");")
                val data = it.select("div.content")
                val name = data.select("h2").text()
                val loc = data.select("p").text()
                val link = data.select("a").attr("href")
                tmpBranches.add(
                    Branch(name,loc,imgUrl,link)
                )
            }
            _branches.value = tmpBranches
            _loading.value = false
        }
    }
}