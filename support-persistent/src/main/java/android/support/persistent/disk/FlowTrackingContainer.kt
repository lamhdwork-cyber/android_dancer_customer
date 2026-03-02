package android.support.persistent.disk

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.CoroutineContext

class FlowTrackingContainer(
    private val debug: Boolean = false
) : CoroutineScope, SqliteTransactionCallback {
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        if (debug) e.printStackTrace()
    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + exceptionHandler + Dispatchers.IO

    private val mTableCache = hashMapOf<String, TableFlowData>()

    private fun notifyTableUpdate(tableName: String) {
        mTableCache[tableName]?.updateChange()
    }

    fun <T> create(
        tableName: String,
        call: suspend () -> T?
    ): Flow<T?> {
        val table = getTable(tableName)
        val flow = MutableStateFlow<T?>(null)

        table.add {
            launch {
                flow.value = call()
            }
        }

        return flow
    }

    private fun getTable(tableName: String): TableFlowData {
        return mTableCache.getOrPut(tableName) { TableFlowData() }
    }

    private class TableFlowData {
        private var mVersion: Int = 0
        private val updateCallbacks = ConcurrentLinkedQueue<() -> Unit>()

        fun add(callback: () -> Unit) {
            updateCallbacks.add(callback)
        }

        fun updateChange() {
            synchronized(this) { mVersion += 1 }
            updateCallbacks.forEach { it() }
        }
    }

    override fun onCommitSucceed(tableName: String) {
        notifyTableUpdate(tableName)
    }
}