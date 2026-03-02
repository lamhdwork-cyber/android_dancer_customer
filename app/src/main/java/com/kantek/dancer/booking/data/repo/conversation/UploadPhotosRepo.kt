package com.kantek.dancer.booking.data.repo.conversation

import android.net.Uri
import com.kantek.dancer.booking.data.extensions.buildMultipart
import com.kantek.dancer.booking.data.extensions.toImagePart
import com.kantek.dancer.booking.data.helper.network.RequestBodyBuilder
import com.kantek.dancer.booking.data.remote.api.ConversationApi
import com.kantek.dancer.booking.domain.factory.PhotoFactory
import okhttp3.MultipartBody

class UploadPhotosRepo(
    private val conversationApi: ConversationApi,
    private val photoFactory: PhotoFactory
) {
    suspend operator fun invoke(
        localID: String,
        requestID: Int,
        photos: List<Uri>
    ) {
        val fileParts: Array<MultipartBody.Part?>? =
            photoFactory.resizeIfNeed(photos)
                ?.mapIndexed { _, s -> s?.toImagePart("images[]") }
                ?.toTypedArray()
        conversationApi.uploadPhotos(
            fileParts,
            RequestBodyBuilder()
                .put("contact_request_id", requestID)
                .put("local_id", localID)
                .buildMultipart()
        ).awaitNullable()
    }
}