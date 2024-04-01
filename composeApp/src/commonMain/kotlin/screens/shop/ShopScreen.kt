package screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import arabicakmp.composeapp.generated.resources.Res
import arabicakmp.composeapp.generated.resources.background
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


object ShopScreen : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.ShoppingCart)

            return remember {
                TabOptions(
                    index = 4u,
                    title = "Online Shop",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ShopScreenModel>()
        val regionalCoffee by screenModel.regionalCoffee.collectAsState()
        val strongCoffee by screenModel.strongCoffee.collectAsState()
        val mediumCoffee by screenModel.mediumCoffee.collectAsState()
        val softCoffee by screenModel.softCoffee.collectAsState()
        val coffeeSet by screenModel.coffeeSet.collectAsState()
        val thermoses by screenModel.thermoses.collectAsState()
        val syrup by screenModel.syrup.collectAsState()
        val tools by screenModel.tools.collectAsState()

//        ArabicaLayout(state.isLoading) {
//            contentUI(state.content)
//        }
        TabScreen(
            listOf(
                regionalCoffee,
                strongCoffee,
                mediumCoffee,
                softCoffee,
                coffeeSet,
                thermoses,
                syrup,
                tools
            )
        )
    }
}

@Composable
fun TabScreen(tabStates: List<TabUiState>) {
    var tabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(selectedTabIndex = tabIndex) {
            Category.entries.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.name) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        contentUI(tabStates[tabIndex].content)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun contentUI(state: List<ShopProduct>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(Res.drawable.background), null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.2f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
//            TextField(
//                value = searchText,
//                onValueChange = onValueChange,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = { Text(text = "Search") }
//            )
//            Spacer(modifier = Modifier.height(16.dp))
            if (false) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyVerticalGrid(
                    GridCells.Adaptive(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(state) { product ->
                        Card(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Box {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.background(Color.Black.copy(0.5f))
                                        .align(Alignment.TopCenter)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        product.name,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                AsyncImage(
                                    product.imgUrl,
                                    Modifier.fillMaxSize()
                                        .aspectRatio(1f)
                                        .zIndex(-1f)
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.background(Color.Black.copy(0.5f))
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                ) {
                                    Text(product.price, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
