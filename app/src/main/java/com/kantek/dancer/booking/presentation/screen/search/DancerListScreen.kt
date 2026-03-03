package com.kantek.dancer.booking.presentation.screen.search

import android.content.Context
import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.local.FilterLocalSource
import com.kantek.dancer.booking.data.remote.api.FilterApi
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.factory.FilterFactory
import com.kantek.dancer.booking.domain.factory.LawyerFactory
import com.kantek.dancer.booking.domain.model.form.LawyerFilterForm
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.search.ICity
import com.kantek.dancer.booking.domain.model.ui.search.ISpeciality
import com.kantek.dancer.booking.domain.model.ui.search.IState
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppDropdown
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.FilterBottomSheet
import com.kantek.dancer.booking.presentation.widget.LawyerItem
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DancerListScreen(viewModel: LawyerListVM = koinViewModel()) = ScopeProvider(Scopes.Search) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    val formState by viewModel.formState.collectAsState()
    val states by viewModel.states.collectAsState()
    val cities by viewModel.cities.collectAsState()
    val languages by viewModel.languages.collectAsState()
    val specialities by viewModel.specialities.collectAsState()
    val lawyers by viewModel.items.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()
    val isLoading by viewModel.customLoading.isLoading().collectAsState()
    val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()

    val isShowRedDot by viewModel.isFilter.collectAsState()

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllState(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_dancer_list) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            AppDropdown(
                                valueSelected = formState.stateSelected,
                                placeHolderRes = R.string.find_select_state,
                                trailingIconRes = R.drawable.ic_arrow_drop_down,
                                items = states,
                            ) { viewModel.updateStateSelected(it) }
                        }

                        SpaceHorizontal(10.dp)

                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier
                                .height(55.dp)
                                .aspectRatio(1f),
                        ) {
                            IconButton(
                                onClick = {
                                    showSheet = true
                                    viewModel.fetchFilter(context)
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(1.dp, Colors.Primary, RoundedCornerShape(8.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filter",
                                    tint = Colors.Primary
                                )
                            }

                            if (isShowRedDot)
                            // Red dot
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .offset(x = (-2).dp, y = 2.dp)
                                        .background(Color.Red, CircleShape)
                                        .align(Alignment.TopEnd)
                                )
                        }
                    }

                    Box(modifier = Modifier.padding(top = 2.dp)) {
                        AppLazyColumn(items = lawyers,
                            keyItem = { it.id },
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            isLoading = isLoading,
                            isRefreshing = isRefreshing,
                            backgroundColor = Color.White,
                            onRefresh = { viewModel.onRefresh() },
                            onLoadMore = { viewModel.onFetch() }) { item, _, _ ->
                            LawyerItem(item,
                                onItemClick = { appNavigator.navigateDetailLawyer(it) },
                                onRequestClick = { appNavigator.navigateQuickRequest(item.owner.toJson()) })
                        }
                        if (isEmpty) NoDataView(htmlRes = R.string.no_data_lawyer)
                    }
                }
            }

            //Filter
            if (showSheet) {
                FilterBottomSheet(
                    sheetState,
                    onDismiss = { showSheet = false },
                    onApply = {
                        showSheet = false
                        viewModel.applyFilter()
                    },
                    onReset = {
                        showSheet = false
                        viewModel.resetFilter(context)
                    },
                    formState.stateSelected != null && formState.stateSelected?.id != -1,
                    selectedCity = formState.citySelected,
                    cities = cities,
                    onSelectCity = { viewModel.updateCitySelected(it) },
                    selectedLanguage = formState.languageSelected,
                    onSelectLanguage = { viewModel.updateLanguageSelected(it) },
                    languages = languages,
                    selectedSpeciality = formState.specialitySelected,
                    specialities = specialities,
                    onSelectSpeciality = { viewModel.updateSpecialitiesSelected(it) }
                )
            }
        }
    }
}

class LawyerListVM(
    private val fetchAllStateRepo: FetchAllStateRepo,
    private val fetchAllCityRepo: FetchAllCityRepo,
    private val fetchAllSpecialityRepo: FetchAllSpecialityRepo,
    private val fetchLawyerListRepo: FetchLawyerListRepo,
    private val filterCurrentRepo: FilterCurrentRepo,
    private val languageRepo: LanguageRepo
) : AppViewModel() {
    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private val _form = MutableStateFlow(LawyerFilterForm())
    val formState: StateFlow<LawyerFilterForm> = _form

    private var page = 1
    private var hasMoreData = true
    private val _items = MutableStateFlow<List<ILawyer>>(emptyList())
    val items: StateFlow<List<ILawyer>> = _items
    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty
    val states = MutableStateFlow<List<IState>>(emptyList())
    val cities = MutableStateFlow<List<ICity>>(emptyList())
    val languages = MutableStateFlow<List<ILanguage>>(emptyList())
    val specialities = MutableStateFlow<List<ISpeciality>>(emptyList())

    val isFilter = MutableStateFlow(false)

    private fun fetchFilterDot() = launch(null, error) {
        isFilter.emit(filterCurrentRepo.hasFilter())
    }

    fun updateStateSelected(it: IState?) {
        if (_form.value.stateSelected != it) {
            _form.value = _form.value.copy(stateSelected = it)
            filterCurrentRepo.saveStateID(it?.id)
            onRefresh()
        }
    }

    fun updateCitySelected(it: ICity?) {
        if (_form.value.citySelected != it) {
            _form.value = _form.value.copy(citySelected = it)
        }
    }

    fun updateLanguageSelected(it: ILanguage?) {
        if (_form.value.languageSelected != it) {
            _form.value = _form.value.copy(languageSelected = it)
        }
    }

    fun updateSpecialitiesSelected(it: List<ISpeciality>?) {
        if (_form.value.specialitySelected != it) {
            _form.value = _form.value.copy(specialitySelected = it)
        }
    }

    fun fetchAllState(context: Context) = launch(loading, error) {
        val rs = fetchAllStateRepo(context)
        updateStateSelected(rs.find { it.id == filterCurrentRepo.getStateID() })
        states.emit(rs)
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        onFetch()
        fetchFilterDot()
    }


    fun onFetch() {
        if (isRefreshLoading.isLoading().value || customLoading.isLoading().value || !hasMoreData) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = fetchLawyerListRepo(page)
            _isEmpty.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) hasMoreData = false
            else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                val current = _items.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _items.value = current + newItems
                page++
            }
        }
    }

    fun fetchFilter(context: Context) = launch(loading, error) {
        cities.value = emptyList()
        languages.value = emptyList()
        specialities.value = emptyList()
        fetchCities()
        fetchLanguages(context)
        fetchSpecialities()
    }

    private fun fetchCities() = launch(loading, error) {
        val stateID = filterCurrentRepo.getStateID()
        if (stateID != -1) {
            val rs = fetchAllCityRepo(stateID)
            cities.emit(rs)
            updateCitySelected(rs.find { it.id == filterCurrentRepo.getCityID() })
        }
    }

    private fun fetchLanguages(context: Context) = launch(loading, error) {
        val rs = languageRepo.fetchAll(context)
        languages.emit(rs)
        updateLanguageSelected(rs.find { it.id == filterCurrentRepo.getLanguageID() })
    }

    private fun fetchSpecialities() = launch(loading, error) {
        val rs = fetchAllSpecialityRepo()
        specialities.emit(rs)
        updateSpecialitiesSelected(rs.filter {
            it.id in (filterCurrentRepo.getSpecialities() ?: emptyList())
        })
    }


    fun applyFilter() = launch(loading, error) {
        filterCurrentRepo.saveFilter(_form.value)
        onRefresh()
    }

    fun resetFilter(context: Context) = launch(loading, error) {
        filterCurrentRepo.reset()
        _form.value = LawyerFilterForm()
        fetchAllState(context)
        onRefresh()
    }

}

class FilterCurrentRepo(private val filterLocalSource: FilterLocalSource) {

    fun getStateID(): Int {
        return filterLocalSource.getStateID()
    }

    fun getCityID(): Int {
        return filterLocalSource.getCityID()
    }

    fun getLanguageID(): Int {
        return filterLocalSource.getLanguageID()
    }

    fun getSpecialities(): List<Int>? {
        return filterLocalSource.getSpecialities()
    }

    fun saveStateID(id: Int?) {
        if (id == null) return
        filterLocalSource.saveStateID(id)
    }

    fun hasFilter(): Boolean {
        if (filterLocalSource.getCityID() != -1) return true
        if (filterLocalSource.getLanguageID() != -1) return true
        if (filterLocalSource.getSpecialities() != null) return true
        return false
    }

    fun saveFilter(it: LawyerFilterForm) {
        filterLocalSource.saveFilter(it)
    }

    fun reset() {
        filterLocalSource.reset()
    }

}

class FetchLawyerListRepo(
    private val lawyerApi: LawyerApi,
    private val lawyerFactory: LawyerFactory,
    private val filterCurrentRepo: FilterCurrentRepo
) {
    suspend operator fun invoke(page: Int): List<ILawyer> {
        val stateID = filterCurrentRepo.getStateID()
        val cityID = filterCurrentRepo.getCityID()
        val languageID = filterCurrentRepo.getLanguageID()
        val specialities = filterCurrentRepo.getSpecialities()
        return lawyerFactory.createList(
            lawyerApi.fetchByPage(
                page = page, stateID = if (stateID == -1) null else stateID,
                cityID = if (cityID == -1) null else cityID,
                languageID = if (languageID == -1) null else languageID,
                jsonSpeciality = if (specialities.isNullOrEmpty()) null else specialities.toJson()
            ).await().data
        ) ?: emptyList()
    }

}

class FetchAllStateRepo(
    private val filterApi: FilterApi,
    private val filterFactory: FilterFactory
) {

    suspend operator fun invoke(context: Context): List<IState> {
        return filterFactory.createStates(context, filterApi.fetchAllState().await())
    }
}

class FetchAllCityRepo(
    private val filterApi: FilterApi,
    private val filterFactory: FilterFactory
) {

    suspend operator fun invoke(stateID: Int): List<ICity> {
        return filterFactory.createCities(filterApi.fetchAllCity(stateID).await())
    }
}

class FetchAllSpecialityRepo(
    private val filterApi: FilterApi,
    private val filterFactory: FilterFactory
) {

    suspend operator fun invoke(): List<ISpeciality> {
        return filterFactory.createSpecialities(filterApi.fetchAllSpeciality().await())
    }
}
