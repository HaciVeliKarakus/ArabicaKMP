package screens.shop

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import screens.product.extractBackgroundImage


class ShopScreenModel : ScreenModel {

    val regionalCoffee: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.RegionalCoffee)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val strongCoffee: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.StrongCoffee)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val mediumCoffee: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.MediumCoffee)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val softCoffee: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.SoftCoffee)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val coffeeSet: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.CoffeeSet)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val thermoses: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.Thermoses)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val syrup: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.Syrup)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    val tools: StateFlow<TabUiState> = flow {
        emit(TabUiState(isLoading = true))
        val content = fetchData(Category.Tools)
        emit(TabUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), TabUiState())


    private suspend fun fetchData(category: Category): List<ShopProduct> {
        val list = mutableListOf<ShopProduct>()
        val url = "${baseUrl}${category.path}"
        val doc = Ksoup.parseGetRequest(url)
        val prods = doc.select("div.product-barrier-shop")
        prods.forEach { item ->
            val name = item.select("div.product-desc").text()
            val price = item.select("span.old").text()
            val imgUrl = item.select("div.product-img").extractBackgroundImage()
            list.add(
                ShopProduct(name = name, price = price, imgUrl = imgUrl, link = "")
            )
        }
        return list.toList()
    }
}

private val baseUrl = "https://shop.arabicacoffee.com.tr/shop/"

@Immutable
data class TabUiState(
    val content: List<ShopProduct> = listOf(),
    val isLoading: Boolean = true
)

enum class Category(val path: String, val label: String) {
    RegionalCoffee("kahveler","Yöresel Kahveler"),
    StrongCoffee("sert-icim","Sert İçim"),
    MediumCoffee("orta-icim","Orta İçim"),
    SoftCoffee("hafif-icim","Hafif içim"),
    CoffeeSet("kahve-setleri", "Kahve Setleri"),
    Thermoses("termoslar","Termoslar"),
    Syrup("suruplar","Şuruplar"),
    Tools("ekipmanlar","Ekipmanlar")
}