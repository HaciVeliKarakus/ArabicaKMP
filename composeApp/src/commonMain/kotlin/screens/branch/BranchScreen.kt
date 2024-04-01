package screens.branch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ArabicaLayout
import components.AsyncImage
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Homeassistantcommunitystore

object BranchScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(SimpleIcons.Homeassistantcommunitystore)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Şubelerimiz",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<BranchScreenModel>()
        val loading by viewModel.loading.collectAsState()
        val branches by viewModel.branches.collectAsState()

        ArabicaLayout(loading) {
            Branches(branches)
        }
    }

    @Composable
    private fun Branches(products: List<Branch>) {
        LazyVerticalGrid(
            GridCells.Adaptive(300.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Box {
                        AsyncImage(
                            url = product.imgUrl,
                            modifier = Modifier.fillMaxSize()
                                .aspectRatio(1f)
                        )
                        Row(
                            modifier = Modifier.align(Alignment.BottomStart)
                                .background(Color.Black.copy(0.5f))
                        ) {
                            Column {
                                Text(
                                    product.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Divider(color = Color.White, thickness = 2.dp)
                                Row {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                    Text(
                                        product.loc,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
