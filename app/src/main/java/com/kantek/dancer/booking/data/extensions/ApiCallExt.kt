package com.kantek.dancer.booking.data.extensions

import com.google.gson.Gson
import com.kantek.dancer.booking.data.helper.file.FileUtils
import com.kantek.dancer.booking.data.helper.network.RequestBodyBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun Map<String, String>.buildParts(): Map<String, RequestBody> {
    val part = hashMapOf<String, RequestBody>()
    forEach {
        part[it.key] = createTextPart(it.value)
    }
    return part
}

fun createTextPart(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaType())
}

fun buildImagePart(name: String, photo: String): MultipartBody.Part {
    val file = File(photo)
    return MultipartBody.Part.createFormData(
        name,
        file.name,
        file.asRequestBody("image/${file.extension}".toMediaType())
    )
}

fun buildVideoPart(field: String, path: String): MultipartBody.Part? {
    val file = File(path)
    if (!file.exists()) return null
    val type = FileUtils.getMimeType(path) ?: return null
    return MultipartBody.Part.createFormData(
        field, file.name,
        file.asRequestBody(type.toMediaTypeOrNull())
    )
}

fun buildPart(name: String, data: Any): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name,
        Gson().toJson(data)
    )
}

fun RequestBodyBuilder.buildMultipart(): Map<String, RequestBody> {
    val multipart = HashMap<String, RequestBody>()
    build().forEach { multipart[it.key] = createValuePart(it.value) }
    return multipart
}

private fun createValuePart(value: String): RequestBody {
    return value.toRequestBody(MultipartBody.FORM)
}

infix fun String.toImagePart(key: String) = buildImagePart(key, this)
infix fun String.toVideoPart(key: String) = buildVideoPart(key, this)
