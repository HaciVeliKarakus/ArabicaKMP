package screens.product

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.select.Elements
import screens.BaseScreenModel

class ProductScreenModel : BaseScreenModel<Product>() {
    override suspend fun fetchData() {
        val tmpProduct = mutableListOf<Product>()
        val url = "https://arabicacoffee.com.tr/urunler"

        val doc = Ksoup.parseGetRequest(url)
        val content = doc.select("div.product-barrier")
        content.forEach {
            val name = it.select("div.product-desc").text()
            val imgUrl = it.select("div.product-img").extractBackgroundImage()
            val link = it.select("a").attr("href")
            tmpProduct.add(
                Product(name, imgUrl, link)
            )
        }

        updateData(tmpProduct)
    }
}


fun Elements.extractBackgroundImage(): String {
    return this.attr("style")
        .split("url('")[1].substringBefore("');")
}
