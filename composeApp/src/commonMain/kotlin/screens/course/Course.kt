package screens.course

import screens.product.BaseDataClass

data class Course(
    val imgUrl: String,
    val title: String,
    val subTitle: String,
    val description: String
) : BaseDataClass {
    override val searchList: List<String>
        get() = listOf(title)
}
