package android.support.ui.helper

fun Any?.edit(): Updatable? {
    return this as? Updatable
}
