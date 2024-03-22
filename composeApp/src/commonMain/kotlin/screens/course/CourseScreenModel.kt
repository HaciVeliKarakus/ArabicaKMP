package screens.course

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseScreenModel : ScreenModel {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(listOf())
    val courses = _courses.asStateFlow()


    init {
        fetchCourses()
    }


    private fun fetchCourses() {
        val tmpProduct = mutableListOf<Course>()
        screenModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val url = "https://arabicacoffee.com.tr/egitimlerimiz"

            val doc = Ksoup.parseGetRequest(url)

            val content = doc.select("div.education-item")
            content.forEach {
                val imgUrl = it.select("img").attr("src")
                val data = it.select("div.education-body")
                val title = data.select("h3").text()
                val text = data.select("p")
                val subtitle = text[0].text()
                val description = text.drop(1).toString()

                tmpProduct.add(
                    Course(
                        imgUrl = imgUrl, title = title,
                        subTitle = subtitle,
                        description = description
                    )
                )
            }
            _courses.value = tmpProduct
            _loading.value = false
        }
    }
}