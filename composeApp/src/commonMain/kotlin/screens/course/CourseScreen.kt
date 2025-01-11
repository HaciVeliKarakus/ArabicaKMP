package screens.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ArabicaLayout
import components.AsyncImage
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Leanpub

object CourseScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(SimpleIcons.Leanpub)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Eğitimlerimiz",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: CourseScreenModel = koinScreenModel()
        val uiState by viewModel.combinedData.collectAsState()
        ArabicaLayout(uiState, viewModel::updateSearchText) {
            CourseItem(it)
        }
    }

    @Composable
    fun CourseItem(course: Course) {
        var showDetail by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.clickable { showDetail = true }
        ) {
            AsyncImage(
                url = course.imgUrl,
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
                        course.title.newLined(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        if (showDetail) {
            CourseDetailScreen(course.description) {
                showDetail = false
            }
        }
    }
}

private fun String.newLined(): String {
    return this.replace("(", "\n(")
}
