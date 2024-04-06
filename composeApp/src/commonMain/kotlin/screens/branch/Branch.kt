package screens.branch

import screens.product.BaseDataClass

data class Branch(
    val name: String,
    val loc: String,
    val imgUrl: String,
    val link: String
) : BaseDataClass {
    override val searchList: List<String>
        get() = listOf(name, loc)
}