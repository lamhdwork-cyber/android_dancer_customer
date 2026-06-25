package android.support.core.network.model

import com.google.gson.annotations.SerializedName

class ApiResponsePaging<T>(
    @SerializedName("items", alternate = ["data"])
    val items: List<T>? = null,
    val meta: ApiPagingMeta? = null,
) {
    val data: List<T>
        get() = items ?: emptyList()
}

class ApiPagingMeta(
    val totalItems: Int? = null,
    val itemCount: Int? = null,
    val itemsPerPage: Int? = null,
    val totalPages: Int? = null,
    val currentPage: Int? = null
)
