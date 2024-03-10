import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import screens.about.AboutScreen
import screens.branch.BranchScreen
import screens.product.ProductScreen

@Composable
@Preview
fun App() {
    initKoin()
    KoinContext {
        MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color(27, 56, 74))) {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    TabNavigator(
        ProductScreen,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(ProductScreen, BranchScreen, AboutScreen)
            )
        }
    ) { tabNavigator ->
        Scaffold(
            topBar = { TopAppBar(title = { Text(tabNavigator.current.options.title) }) },
            content = { CurrentTab() },
            bottomBar = {
                BottomNavigation {
                    TabNavigationItem(ProductScreen)
                    TabNavigationItem(BranchScreen)
                    TabNavigationItem(AboutScreen)
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}
