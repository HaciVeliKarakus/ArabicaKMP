package screens.product

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

class ProductScreenModel : ScreenModel {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(listOf())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    val products = searchText
        .debounce(500.milliseconds)
        .onEach { _isSearching.update { true } }
        .combine(_products) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                delay(500.milliseconds)
                products.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000),
            _products.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        val tmpProduct = mutableListOf<Product>()
        screenModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val url = "https://arabicacoffee.com.tr/urunler"

            val doc = Ksoup.parseGetRequest(url)
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

            _products.value = tmpProduct
            _loading.value = false
        }
    }
}

