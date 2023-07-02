package kopycinski.tomasz.klamkify.ui.screens.activities

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kopycinski.tomasz.domain.model.Activity
import kopycinski.tomasz.domain.model.Category
import kopycinski.tomasz.domain.usecase.GetCategoriesWithActivitiesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class ActivityListUIState(
    val categories: Map<Category, List<Activity>> = mapOf(),
    val extendedCategory: Long = -1
)

@HiltViewModel
class ActivityListViewModel @Inject constructor(
    private val getCategoriesWithActivitiesUseCase: GetCategoriesWithActivitiesUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(ActivityListUIState())
    val uiState: State<ActivityListUIState> = _uiState

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesWithActivitiesUseCase()
            .onEach {
                _uiState.value = ActivityListUIState(it)
            }
            .launchIn(viewModelScope)
    }

    fun toggleCategory(categoryId: Long) {
        _uiState.value = if (_uiState.value.extendedCategory == categoryId) {
            _uiState.value.copy(extendedCategory = -1)
        } else {
            _uiState.value.copy(extendedCategory = categoryId)
        }
    }
}