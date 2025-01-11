package components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResponsiveText(text: String) {
    BoxWithConstraints {
        val screenWidth = maxWidth
        val textSize = when {
            screenWidth < 300.dp -> 12.sp
            screenWidth < 600.dp -> 16.sp
            else -> 20.sp
        }

        Text(
            text = text,
            fontSize = textSize,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}