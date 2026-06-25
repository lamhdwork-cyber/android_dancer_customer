package android.support.ui.app

import android.support.core.event.ErrorEvent
import android.support.core.event.LoadingEvent
import android.support.core.event.WindowStatusOwner
import androidx.lifecycle.ViewModel

/**
 * Base ViewModel wiring the shared loading / error window status. Copyable as-is;
 * app-specific state (user, language, …) lives in the app subclass.
 */
abstract class BaseViewModel : ViewModel(), WindowStatusOwner {
    private val windowStatusOwner = BaseComponentAct.WindowStatusProvider.instance

    override val loading: LoadingEvent
        get() = windowStatusOwner.loading

    override val error: ErrorEvent
        get() = windowStatusOwner.error

    override val refreshLoading: LoadingEvent
        get() = windowStatusOwner.refreshLoading
}