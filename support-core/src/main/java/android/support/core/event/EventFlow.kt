package android.support.core.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.atomic.AtomicBoolean

fun <T> singleEventFlowOf(): SingleEventFlow<T> = SingleEventFlowImpl<T>()

interface SingleEventFlow<T> : Flow<T>, FlowCollector<T> {
    fun post(value: T)
}

private open class SingleEventFlowImpl<T>(
    private val flow: MutableSharedFlow<T> = MutableSharedFlow<T>(1, 1, BufferOverflow.DROP_OLDEST)
) : SingleEventFlow<T> {
    private val mPending = AtomicBoolean(false)

    override suspend fun collect(collector: FlowCollector<T>) {
        flow.collect {
            if (mPending.compareAndSet(true, false)) {
                collector.emit(it)
            }
        }
    }

    override fun post(value: T) {
        mPending.set(true)
        flow.tryEmit(value)
    }

    override suspend fun emit(value: T) {
        mPending.set(true)
        flow.emit(value)
    }
}