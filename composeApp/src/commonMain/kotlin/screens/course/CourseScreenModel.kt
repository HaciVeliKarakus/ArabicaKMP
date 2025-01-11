package screens.course

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import screens.BaseScreenModel

class CourseScreenModel : BaseScreenModel<Course>() {

    override suspend fun fetchData() {
        val tmpProduct = mutableListOf<Course>()

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
        updateData(tmpProduct)
    }
}
