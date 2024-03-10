package screens.product

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Product
import org.jsoup.Jsoup

class ProductScreenModel : ScreenModel {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products = _products.asStateFlow()


    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        val tmpProduct = mutableListOf<Product>()
        screenModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val url = "https://arabicacoffee.com.tr/urunler"

            val doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(55_000)
                .get()
            val content = doc.select("div.product-barrier")
            content.forEach {
                val name = it.select("div.product-desc").text()
                val imgUrl = it.select("div.product-img").attr("style")
                    .split("url('")[1].substringBefore("');")
                val link = it.select("a").attr("href")
                tmpProduct.add(
                    Product(name, imgUrl, link)
                )
            }
            println("____size ${tmpProduct.size}")
            _products.value = tmpProduct
            _loading.value = false
        }
    }
}

