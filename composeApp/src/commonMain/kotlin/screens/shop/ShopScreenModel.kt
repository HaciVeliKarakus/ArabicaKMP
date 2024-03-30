package screens.shop

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

    val state: StateFlow<AboutUiState> = flow {
        emit(AboutUiState(isLoading = true))
        val content = fetchAbout()
        emit(AboutUiState(isLoading = false, content = content))
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5_000), AboutUiState())

    private suspend fun fetchAbout(): List<ShopProduct> {
        val list = mutableListOf<ShopProduct>()
        val url = "https://shop.arabicacoffee.com.tr/shop/kahveler"
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


data class AboutUiState(
    val isLoading: Boolean = true,
    val content: List<ShopProduct> = listOf()
)