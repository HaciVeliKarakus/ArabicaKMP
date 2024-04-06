package screens.product

import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.select.Elements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import screens.BaseScreenModel

class ProductScreenModel : BaseScreenModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val backgroundFlowImage = "https://arabicacoffee.com.tr/assets/img/sliders/slider-5.jpg"

    val products = searchableFlow(_products)
    override fun fetchData() {
        val tmpProduct = mutableListOf<Product>()
        screenModelScope.launch(Dispatchers.IO) {
            updateLoading(true)

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

            _products.value = tmpProduct
            updateLoading(false)
        }
    }
}


fun Elements.extractBackgroundImage(): String {
    return this.attr("style")
        .split("url('")[1].substringBefore("');")
}
