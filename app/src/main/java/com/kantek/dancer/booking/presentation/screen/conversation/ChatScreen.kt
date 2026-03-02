package com.kantek.dancer.booking.presentation.screen.conversation

import android.app.Activity
import android.net.Uri
import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import android.support.core.extensions.parcelableArrayList
import android.support.core.extensions.safe
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppSettings
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.repo.conversation.ChatRepo
import com.kantek.dancer.booking.data.repo.conversation.ChatSocketRepo
import com.kantek.dancer.booking.data.repo.conversation.FetchMessageByPageRepo
import com.kantek.dancer.booking.data.repo.conversation.UploadPhotosRepo
import com.kantek.dancer.booking.domain.factory.ConversationFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.conversation.Message
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.loge
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.LoadingView
import com.sangcomz.fishbun.FishBun
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun ChatScreen(
    bookingID: Int,
    viewModel: ConversationVM = koinViewModel()
) = ScopeProvider(Scopes.Conversation) {
    val context = LocalContext.current
    val isFirstLoading by viewModel.firstLoading.isLoading().collectAsState()

    val appNavigator = use<AppNavigator>(Scopes.App)
    var message by remember { mutableStateOf("") }
    val isEmpty by viewModel.isEmpty.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    val isTyping by viewModel.isTyping.collectAsState()
    val scrollToFirst by viewModel.isScrollToFirst.collectAsState()
    val isLoading by viewModel.customLoading.isLoading().collectAsState()

    LaunchedEffect(bookingID) {
        viewModel.run {
            setChatID(bookingID)
            joinRoom()
            onRefresh()
        }
    }

    LaunchedEffect(scrollToFirst) {
        if (scrollToFirst) {
            listState.animateScrollToItem(0)
            viewModel.isScrollToFirst.value = false
        }
    }

    DisposableEffect(bookingID) {
        onDispose { viewModel.disconnectSocket() }
    }


    var imageUriPending by remember { mutableStateOf<Uri?>(null) }
    val appSetting = remember { AppSettings(context) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photos: List<Uri> =
                result.data?.parcelableArrayList(FishBun.INTENT_PATH) ?: listOf()
            viewModel.sendPhotos(photos)
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (imageUriPending != null)
                viewModel.sendPhotos(listOf(imageUriPending!!))
        }
    }

    PermissionProvider {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Gray249)
        ) {
            ActionBarConversationView(bookingID = bookingID) { appNavigator.back() }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                ChatLazyColumn(
                    items = messages,
                    state = listState,
                    isLoading = isLoading,
                    onLoadMore = { viewModel.fetchMessage() },
                    modifier = Modifier.fillMaxSize()
                ) {
                    ChatBubble(it) { appNavigator.navigatePhotoViewer(it) }
                }

                if (isEmpty) {
                    NoDataConversationView()
                }
            }
            AnimatedVisibility(
                visible = isTyping,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    LottieAnimation(
                        composition = rememberLottieComposition(LottieCompositionSpec.Asset("typing_animation.json")).value,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            ChatInputBox(
                messageText = message,
                onMessageChange = {
                    message = it
                    viewModel.setTyping(it)
                },
                onSend = {
                    viewModel.sendTextMessage(message.trim())
                    message = ""
                },
                onOpenCamera = {
                    accessCapture {
                        appSetting.openCameraForImage(cameraLauncher) {
                            imageUriPending = it
                        }
                    }
                },
                onOpenGallery = {
                    accessReadImage {
                        appSetting.openGalleryForImagesChat(galleryLauncher)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            LoadingView(isFirstLoading, R.string.conversation_loading)
        }
    }
}

class ConversationVM(
    private val fetchMessageByPageRepo: FetchMessageByPageRepo,
    private val chatSocketRepo: ChatSocketRepo,
    private val chatRepo: ChatRepo,
    private val conversationFactory: ConversationFactory,
    private val uploadPhotosRepo: UploadPhotosRepo
) : AppViewModel() {
    private var bookingID = 0
    private var page = 1
    private var hasMoreData = true
    val isEmpty = MutableStateFlow(true)

    val customLoading: LoadingEvent = LoadingFlow()
    val firstLoading: LoadingEvent = LoadingFlow()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages
    val isTyping = MutableStateFlow(false)
    val isScrollToFirst = MutableStateFlow(false)
    private var typingResetJob: Job? = null

    init {
        socketReceivedMessageListener()
        socketLeaveListener()
        socketTypingListener()
    }

    private fun socketTypingListener() = launch(error = error) {
        chatSocketRepo.typing.collect {
            isTyping.emit(true)

            typingResetJob?.cancel()
            typingResetJob = viewModelScope.launch {
                delay(2000)
                isTyping.emit(false)
            }
        }
    }

    private fun socketLeaveListener() = launch(error = error) {
        chatSocketRepo.leave.collect {
            isTyping.emit(false)
        }
    }

    private fun socketReceivedMessageListener() = launch(error = error) {
        chatSocketRepo.receiveMessage.collect { msgDTO ->
            if (msgDTO == null) return@collect
            _messages.update { list ->
                val matched = list.any { it.localId == msgDTO.local_id }
                if (matched) {
                    list.map {
                        if (it.localId == msgDTO.local_id) it.copy(isSending = false)
                        else it
                    }
                } else {
                    val newMessage = fetchMessageByPageRepo.newMessage(msgDTO, _messages.value)
                    loge("receiveMessage$msgDTO\n$newMessage")
                    listOf(newMessage) + list
                }
            }
            isEmpty.value = false
            scrollToFirst()
        }
    }

    private fun scrollToFirst() = launch(error = error) {
        delay(100)
        isScrollToFirst.emit(true)
    }

    fun setChatID(id: Int) = launch(null, error) {
        bookingID = id
        chatRepo.setChatRoomCurrent(id)
    }

    fun joinRoom() = launch(null, error) {
        chatSocketRepo.emitJoinRoom(chatRepo.getChatRoomCurrent())
    }

    private fun leaveRoom() = launch(null, error) {
        chatSocketRepo.emitLeaveRoom(chatRepo.getChatRoomCurrent())
    }

    fun disconnectSocket() = launch(null, error) {
        clearRoom()
        leaveRoom()
        chatSocketRepo.disconnect()
    }

    fun onRefresh() {
        if (_messages.value.isEmpty()) {
            page = 1
            hasMoreData = true
            _messages.value = emptyList()
            fetchMessage()
        }
    }

    fun fetchMessage() {
        if (loading.isLoading().value
            || customLoading.isLoading().value
            || !hasMoreData
        ) return
        launch(if (page == 1) firstLoading else customLoading, error) {
            val rs = fetchMessageByPageRepo(bookingID, page)
            isEmpty.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) hasMoreData = false
            else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                val current = _messages.value
                val newMessages = rs.filterNot { newMsg ->
                    current.any { it.id == newMsg.id }
                }
                _messages.value = current + newMessages
                page++
            }
        }
    }

    fun sendTextMessage(textMsg: String) = launch(null, error) {
        val newMessage = conversationFactory.createTextMessage(textMsg)
        _messages.update { (listOf(newMessage) + it) }
        isEmpty.value = false
        scrollToFirst()
        chatSocketRepo.sendMessage(
            chatRepo.getChatRoomCurrent(),
            newMessage.message,
            newMessage.localId.safe()
        )
    }

    //Upload and server will auto send message
    fun sendPhotos(photos: List<Uri>) = launch(null, error) {
        val newMessage = conversationFactory.createPhotosMessage(photos)
        _messages.update { (listOf(newMessage) + it) }
        isEmpty.value = false
        scrollToFirst()
        uploadPhotosRepo(newMessage.localId, chatRepo.getChatRoomCurrent(), photos)
    }

    private fun clearRoom() = launch(error = error) {
        loge("clearRoom")
        chatRepo.setChatRoomCurrent(0)
    }

    fun setTyping(it: String) = launch(error = error) {
        chatSocketRepo.emitTyping(chatRepo.getChatRoomCurrent(), it)
    }
}