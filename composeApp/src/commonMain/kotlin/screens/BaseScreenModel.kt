package screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import screens.product.BaseDataClass
import kotlin.time.Duration.Companion.milliseconds

open class BaseScreenModel<T : BaseDataClass> : ScreenModel {
    protected open val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _uiState = MutableStateFlow(UIState<T>())
    private val _data = MutableStateFlow<List<T>>(listOf())
    private val _loading = MutableStateFlow(false)
    private val _searchText = MutableStateFlow("")

    protected open suspend fun fetchData() {}

    val combinedData = combine(
        _loading,
        _uiState,
        ::CombinedData
    ).stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CombinedData()
    )

    init {
        setup {
            fetchData()
        }
        initState()
    }

    @OptIn(FlowPreview::class)
    private fun initState() {
        screenModelScope.launch {
            _uiState
                .onEach { _loading.update { true } }
                .debounce(500)
                .map { it.searchText.trim() }
                .combine(_data) { searchText, dataList ->
                    if (searchText.isBlank()) {
                        _uiState.update { it.copy(data = dataList) }
                    } else {
                        delay(500.milliseconds)
                        _uiState.update { currentUIState ->
                            currentUIState.copy(
                                data = dataList.filter {
                                    it.searchList.any { field ->
                                        field.contains(searchText, ignoreCase = true)
                                    }
                                },
                            )
                        }
                    }
                }
                .collect {
                    _uiState.update { it }
                    _loading.update { false }
                }
        }
    }

    protected fun updateData(data: List<T>) {
        _data.update { data }
        _uiState.update {
            it.copy(data = data)
        }
    }

    fun updateSearchText(value: String) {
        _uiState.update {
            it.copy(searchText = value)
        }
    }

    private fun setup(
        block: suspend CoroutineScope.() -> Unit
    ) {
        screenModelScope.launch(dispatcher) {
            _loading.update { true }
            try {
                block()
            } finally {
                _loading.update { false }
            }
        }
    }
}

data class CombinedData<T>(
    val loading: Boolean = false,
    val uiState: UIState<T> = UIState()
)

data class UIState<T>(
    val error: Boolean = false,
    val searchText: String = "",
    val data: List<T>? = null,
)


