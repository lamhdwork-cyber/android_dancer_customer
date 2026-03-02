package android.support.core.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

interface Event<T> {
    fun post(value: T)

    fun observe(owner: LifecycleOwner, observer: Observer<in T>)

    fun Flow<T>.observe(
        owner: LifecycleOwner,
        doNotify: (T) -> Unit
    ) {
        owner.lifecycleScope.launch {
            filterNotNull()
                .distinctUntilChanged()
                .flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { doNotify(it) }
        }
    }
}

interface ErrorEvent : Event<Throwable>

interface LoadingEvent : Event<Boolean> {
    fun start() {
        post(true)
    }

    fun stop() {
        post(false)
    }

    fun isLoading(): StateFlow<Boolean>
}