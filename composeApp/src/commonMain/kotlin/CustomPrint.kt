fun customPrint(item: Any?, tag: String = "CP") {
    val caller = Throwable().stackTrace.getOrNull(2)
    println("[$tag][${caller?.fileName}:${caller?.lineNumber}] ${item.toString()}")
}
