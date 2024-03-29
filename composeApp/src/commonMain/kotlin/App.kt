import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import screens.shop.ShopScreen
import screens.branch.BranchScreen
import screens.course.CourseScreen
import screens.product.ProductScreen

@Composable
fun App() {
    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color(27, 56, 74))) {
        AppContent()
    }
}

@Composable
private fun AppContent() {
    TabNavigator(ProductScreen) { tabNavigator ->
        Scaffold(
            topBar = { TopAppBar(title = { Text(tabNavigator.current.options.title) }) },
            content = {
                Column {
                    CurrentTab()
                    Spacer(Modifier.height(56.dp))
                }
            },
            bottomBar = {
                BottomNavigation {
                    TabNavigationItem(ProductScreen)
                    TabNavigationItem(BranchScreen)
                    TabNavigationItem(CourseScreen)
                    TabNavigationItem(ShopScreen)
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
