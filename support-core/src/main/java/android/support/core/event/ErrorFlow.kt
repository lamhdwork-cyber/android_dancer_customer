package android.support.core.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class ErrorFlow : ErrorEvent {
    private val _event = singleEventFlowOf<Throwable>()

    override fun post(value: Throwable) {
        _event.post(value)
    }

    fun post(message: String) {
        post(Exception(message))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in Throwable>) {
        _event.observe(owner) { observer.onChanged(it) }
    }
}