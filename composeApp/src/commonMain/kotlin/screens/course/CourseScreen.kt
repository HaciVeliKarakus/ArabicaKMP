package screens.course

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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

object CourseScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Product",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<CourseScreenModel>()
        val courses by viewModel.courses.collectAsState()
        val loading by viewModel.loading.collectAsState()

        ArabicaLayout(loading) {
            CoursesUI(courses)
        }
    }

    @Composable
    private fun CoursesUI(products: List<Course>) {
        var showDetail by remember { mutableStateOf(false) }
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
//                        .onClick {
//                            showDetail = true
//                        }
                ) {
                    Box {
                        AsyncImage(
                            url = product.imgUrl,
                            modifier = Modifier.fillMaxSize()
                                .aspectRatio(1f)
                        )
                        Row(
                            modifier = Modifier.align(Alignment.BottomStart).background(
                                Color.Black.copy(0.5f)
                            )
                        ) {
                            Column {
                                Divider(color = Color.White, thickness = 2.dp)

                                Text(
                                    product.title.newLined(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                            }

                        }
                    }
                }
                if (showDetail) {
                    CourseDetailScreen(product.description) {
                        showDetail = false
                    }
                }
            }
        }


    }
}

private fun String.newLined(): String {
    return this.replace("(", "\n(")
}
