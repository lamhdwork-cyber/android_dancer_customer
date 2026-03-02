package android.support.core.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.MutableStateFlow

class LoadingFlow : LoadingEvent {
    private val _flow = MutableStateFlow(false)

    override fun isLoading() = _flow

    override fun post(value: Boolean) {
        _flow.value = value
    }

    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in Boolean>
    ) {
        _flow.observe(owner) {
            observer.onChanged(it)
        }
    }
}
