package android.support.ui.helper

interface Updatable {
    fun update(value: Any?, notify: Boolean = false)
}