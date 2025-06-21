package components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import arabicakmp.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.CombinedData
import screens.UIState
import screens.product.BaseDataClass

@Composable
fun <T : BaseDataClass> MainLayout(
    combinedData: CombinedData<T>,
    onSearch: (String) -> Unit,
    itemLayout: @Composable (T) -> Unit
) {
    SuperSearchBar(combinedData.uiState.searchText, onSearch)
    AnimatedContent(combinedData.loading) {
        when (it) {
            true -> RotatingLogo()
            false -> ContentUI(combinedData.uiState, itemLayout)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun <T : BaseDataClass> ContentUI(
    uiState: UIState<T>,
    itemLayout: @Composable (T) -> Unit
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/not_found_2.json").decodeToString()
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                GridCells.Adaptive(200.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                if (uiState.data != null) {
                    items(uiState.data) { item ->
                        Card(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            itemLayout(item)
                        }
                    }
                }
            }
            if (uiState.data?.isEmpty() == true) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberLottiePainter(
                            composition = composition,
                            iterations = Compottie.IterateForever
                        ),
                        contentDescription = "Lottie animation",
                        modifier = Modifier.widthIn(max = 300.dp)
                    )
                    ResponsiveText(
                        "Arama sonucu bulunamadı!"
                    )
                }
            }
        }
    }
}