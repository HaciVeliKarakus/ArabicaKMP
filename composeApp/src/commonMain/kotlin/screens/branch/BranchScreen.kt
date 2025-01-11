package screens.branch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
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
        val viewModel:BranchScreenModel = koinScreenModel()
        val combinedData by viewModel.combinedData.collectAsState()
        ArabicaLayout(combinedData ,viewModel::updateSearchText) {
           BranchItem(it)
        }
    }

    @Composable
    private fun BranchItem(branch: Branch) {
       Box {
           AsyncImage(
               url = branch.imgUrl,
               modifier = Modifier.fillMaxSize()
                   .aspectRatio(1f)
           )
           Row(
               modifier = Modifier.align(Alignment.BottomStart)
                   .background(Color.Black.copy(0.5f))
           ) {
               Column {
                   Text(
                       branch.name,
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
                           branch.loc,
                           color = Color.White
                       )
                   }
               }
           }
       }
    }
}