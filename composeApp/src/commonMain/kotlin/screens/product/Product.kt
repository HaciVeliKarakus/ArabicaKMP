package screens.product

data class Product(
    val name: String,
    val imgUrl: String?,
    val url: String
) : BaseDataClass {
    override val searchList: List<String>
        get() = listOf(name)
}


interface BaseDataClass {
    val searchList: List<String>
}