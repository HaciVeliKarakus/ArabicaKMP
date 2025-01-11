package components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SuperSearchBar(
    keyword: String,
    updateSearchText: (String) -> Unit
) {
    TextField(
        value = keyword,
        onValueChange = updateSearchText,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") }
    )
}