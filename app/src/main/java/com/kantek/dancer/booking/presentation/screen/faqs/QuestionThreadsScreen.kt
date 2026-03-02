package com.kantek.dancer.booking.presentation.screen.faqs

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.FAQsThreadsApi
import com.kantek.dancer.booking.domain.extension.resourceError
import com.kantek.dancer.booking.domain.factory.FAQsThreadsFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalAnswer
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalQuestion
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.FAQThreadsQuestionItem
import com.kantek.dancer.booking.presentation.widget.FAQsAnswerBottomSheet
import com.kantek.dancer.booking.presentation.widget.FQAsLoading
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.QuestionSentDialog
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import com.kantek.dancer.booking.presentation.widget.SubmitAnswerDialog
import com.kantek.dancer.booking.presentation.widget.SubmitQuestionDialog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionThreadsScreen(
    categoryID: Int = -1, categoryName: String = "", viewModel: QuestionThreadsVM = koinViewModel()
) = ScopeProvider(Scopes.FAQsThreads) {
    val appNavigator = use<AppNavigator>(Scopes.App)

    val faqsList by viewModel.categories.collectAsState()
    val isEmpty by viewModel.isEmptyFAQs.collectAsState()
    val isLoading by viewModel.loadingFAQs.isLoading().collectAsState()
    val isMoreLoading by viewModel.loadingMoreFAQs.isLoading().collectAsState()

    var hasShowEnterQuestion by remember { mutableStateOf(false) }
    val createQuestionSuccess by viewModel.createQuestionSuccess.collectAsState()
    var hasShowSentDialog by remember { mutableStateOf(false) }

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val answers by viewModel.answers.collectAsState()
    val isMoreLoadingAnswer by viewModel.loadingMoreAnswer.isLoading().collectAsState()
    var questionName by remember { mutableStateOf("") }

    var hasShowEnterAnswer by remember { mutableStateOf(false) }
    val createAnswerSuccess by viewModel.createAnswerSuccess.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshFAQs(categoryID)
    }

    LaunchedEffect(createQuestionSuccess) {
        if (createQuestionSuccess) {
            hasShowEnterQuestion = false
            hasShowSentDialog = true
        }
    }

    LaunchedEffect(createAnswerSuccess) {
        if (createAnswerSuccess) {
            hasShowEnterAnswer = false
            hasShowSentDialog = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ActionBarBackAndTitleView(categoryName) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            when {
                isLoading -> FQAsLoading()
                isEmpty -> NoDataView(htmlRes = R.string.no_data_faqs)
                faqsList.isNotEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp)
                            .background(Color.White)
                    ) {

                        SpaceVertical(14.dp)

                        AppButton(
                            nameRes = R.string.all_submit_your_question,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                        ) {
                            hasShowEnterQuestion = true
                        }

                        SpaceVertical(20.dp)

                        AppLazyColumn(items = faqsList,
                            keyItem = { it.id },
                            isLoading = isMoreLoading,
                            onLoadMore = { viewModel.fetchFAQs(categoryID) }) { item, _, _ ->
                            FAQThreadsQuestionItem(item) {
                                questionName = it.name
                                showSheet = true
                                viewModel.setQuestion(it)
                            }
                        }
                    }
                }
            }

            if (hasShowEnterQuestion) {
                SubmitQuestionDialog(onDismiss = { hasShowEnterQuestion = false },
                    onConfirm = { viewModel.submitQuestion(categoryID, it) })
            }

            if (hasShowEnterAnswer) {
                SubmitAnswerDialog(onDismiss = { hasShowEnterAnswer = false },
                    onConfirm = { viewModel.submitAnswer(it) })
            }

            if (hasShowSentDialog) {
                QuestionSentDialog { hasShowSentDialog = false }
            }
        }

        if (showSheet) {
            FAQsAnswerBottomSheet(
                sheetState = sheetState,
                items = answers,
                title = questionName,
                isMoreLoading = isMoreLoadingAnswer,
                onDismiss = { showSheet = false },
                onReply = { hasShowEnterAnswer = true },
                onLoadMore = { viewModel.fetchAnswers() }
            )
        }
    }
}

class QuestionThreadsVM(
    private val fetchQuestionThreadsByPage: FetchQuestionThreadsByPage,
    private val submitQuestionRepo: SubmitQuestionRepo,
    private val fetchAnswerThreadsByPage: FetchAnswerThreadsByPage,
    private val submitAnswerRepo: SubmitAnswerRepo
) : AppViewModel() {

    private var page = 1
    private var hasMoreData = true
    val loadingFAQs: LoadingEvent = LoadingFlow()
    val loadingMoreFAQs: LoadingEvent = LoadingFlow()
    private val _faqs = MutableStateFlow<List<ILegalQuestion>>(emptyList())
    val categories: StateFlow<List<ILegalQuestion>> = _faqs
    val isEmptyFAQs = MutableStateFlow(false)

    //Answers
    private var question: ILegalQuestion? = null
    private var pageAnswer = 1
    private var hasMoreDataAnswer = true
    val loadingMoreAnswer: LoadingEvent = LoadingFlow()
    private val _answers = MutableStateFlow<List<ILegalAnswer>>(emptyList())
    val answers: StateFlow<List<ILegalAnswer>> = _answers
    val isEmptyAnswer = MutableStateFlow(false)

    val createQuestionSuccess = submitQuestionRepo.result
    val createAnswerSuccess = submitAnswerRepo.result

    fun refreshFAQs(categoryID: Int) {
        page = 1
        hasMoreData = true
        isEmptyFAQs.value = false
        _faqs.value = emptyList()
        fetchFAQs(categoryID)
    }

    fun fetchFAQs(categoryID: Int) {
        if ((loadingFAQs.isLoading().value && page == 1) || loadingMoreFAQs.isLoading().value || !hasMoreData) return
        launch(if (page == 1) loadingFAQs else loadingMoreFAQs, error) {
            val rs = fetchQuestionThreadsByPage(categoryID, page)
            isEmptyFAQs.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) {
                hasMoreData = false
            } else {
                val current = _faqs.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _faqs.value = current + newItems
                page++
            }
        }
    }

    fun submitQuestion(id: Int, it: Pair<String, String>) = launch(loading, error) {
//        if (it.first.isEmpty()) resourceError(R.string.error_blank_title)
        if (it.second.isEmpty()) resourceError(R.string.error_blank_content)
        submitQuestionRepo(id = id, title = it.first, contents = it.second)
    }

    fun submitAnswer(it: String) = launch(loading, error) {
        if (it.isEmpty()) resourceError(R.string.error_blank_content)
        submitAnswerRepo(id = question?.id!!, contents = it)
    }

    private fun refreshAnswers() {
        pageAnswer = 1
        hasMoreDataAnswer = true
        isEmptyAnswer.value = false
        _answers.value = emptyList()
        fetchAnswers()
    }

    fun fetchAnswers() {
        if (loadingMoreAnswer.isLoading().value || !hasMoreDataAnswer || question == null) return
        launch(loadingMoreAnswer, error) {
            val rs = fetchAnswerThreadsByPage(question!!.id, pageAnswer)
            isEmptyAnswer.value = (pageAnswer == 1 && rs.isEmpty())
            if (rs.isEmpty()) {
                hasMoreDataAnswer = false
            } else {
                val current = _answers.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _answers.value = current + newItems
                pageAnswer++
            }
        }
    }

    fun setQuestion(it: ILegalQuestion) = launch(loading, error) {
        question = it
        refreshAnswers()
    }
}

class FetchAnswerThreadsByPage(
    private val faqsAPI: FAQsThreadsApi,
    private val faqsFactory: FAQsThreadsFactory
) {
    suspend operator fun invoke(questionID: Int, page: Int): List<ILegalAnswer> {
        return faqsFactory.createAnswers(
            faqsAPI.fetchAnswersByPage(questionID, page).awaitNullable()
        )
    }
}

class SubmitQuestionRepo(private val faqsAPI: FAQsThreadsApi) {
    val result = MutableStateFlow(false)
    suspend operator fun invoke(id: Int, title: String, contents: String) {
        faqsAPI.createQuestion(id, contents).await()
        result.emit(true)
    }

}

class SubmitAnswerRepo(private val faqsAPI: FAQsThreadsApi) {
    val result = MutableStateFlow(false)
    suspend operator fun invoke(id: Int, contents: String) {
        faqsAPI.createAnswer(id, contents).await()
        result.emit(true)
    }

}

class FetchQuestionThreadsByPage(
    private val faqsAPI: FAQsThreadsApi,
    private val faqsFactory: FAQsThreadsFactory
) {
    suspend operator fun invoke(categoryID: Int, page: Int): List<ILegalQuestion> {
        return faqsFactory.createQuestions(
            faqsAPI.fetchQuestionOfCategoriesByPage(categoryID, page).awaitNullable()?.data
        )
    }
}
