package screens.branch

import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import screens.BaseScreenModel

class BranchScreenModel : BaseScreenModel() {

    private val _branches = MutableStateFlow<List<Branch>>(listOf())
    val branches = searchableFlow(_branches)

    init {
        fetchBranches()
    }

    private fun fetchBranches() {
        val tmpBranches = mutableListOf<Branch>()
        screenModelScope.launch(Dispatchers.IO) {
            updateLoading(true)

            val url = "https://arabicacoffee.com.tr/subeler"

            val doc = Ksoup.parseGetRequest(url)

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
                    Branch(name, loc, imgUrl, link)
                )
            }
            _branches.value = tmpBranches
            updateLoading(false)
        }
    }
}