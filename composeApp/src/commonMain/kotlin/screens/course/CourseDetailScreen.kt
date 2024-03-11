package screens.course

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@Composable
fun CourseDetailScreen(description: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Text(description, color = Color.White)
    }
}