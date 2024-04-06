package screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import screens.product.BaseDataClass
import kotlin.time.Duration.Companion.milliseconds

open class BaseScreenModel : ScreenModel {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    protected fun <T:BaseDataClass> searchableFlow(
        data: MutableStateFlow<List<T>>
    ): StateFlow<List<T>> = searchText
        .debounce(500.milliseconds)
        .onEach { updateSearching(true) }
        .combine(data) { searchText, dataList ->
            if (searchText.isBlank()) {
                dataList
            } else {
                delay(500.milliseconds)
                dataList.filter {
                    it.searchList.any{ field ->
                        field.contains(searchText,ignoreCase = true)
                    }
                }
            }
        }
        .onEach { updateSearching(false) }
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    protected fun updateLoading(value: Boolean) = _loading.update { value }
    private fun updateSearching(value: Boolean) = _isSearching.update { value }
    fun updateSearchText(value: String) = _searchText.update { value }

    protected open fun fetchData() {}
    init {
        this.fetchData()
    }
}