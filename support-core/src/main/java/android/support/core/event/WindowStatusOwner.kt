package android.support.core.event

interface WindowStatusOwner {
    val error: ErrorEvent
    val loading: LoadingEvent
    val refreshLoading: LoadingEvent
}

class StateFlowStatusOwner : WindowStatusOwner {
    override val error: ErrorEvent = ErrorFlow()
    override val loading: LoadingEvent = LoadingFlow()
    override val refreshLoading: LoadingEvent = LoadingFlow()
}