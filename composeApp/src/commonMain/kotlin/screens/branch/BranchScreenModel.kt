package screens.branch

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import screens.BaseScreenModel

class BranchScreenModel : BaseScreenModel<Branch>() {


    override suspend fun fetchData() {
        val tmpBranches = mutableListOf<Branch>()

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
        updateData(tmpBranches)
    }
}