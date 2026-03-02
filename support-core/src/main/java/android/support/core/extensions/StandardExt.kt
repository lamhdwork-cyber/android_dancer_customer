package android.support.core.extensions

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.util.*

fun <T> block(any: T?, function: T.() -> Unit) {
    if (any != null) function(any)
}

fun String?.safe(def: String = ""): String {
    return this ?: def
}

fun List<String>?.safe(def: List<String> = listOf()): List<String> {
    return this ?: def
}

fun Long?.safe(def: Long = 0): Long {
    return this ?: def
}

fun String?.or(def: String = ""): String {
    if (this != null) {
        if (this.isBlank()) return def
        return this
    }
    return def
}

fun Int?.safe(def: Int = 0): Int {
    return this ?: def
}

fun Double?.safe(def: Double = 0.0): Double {
    return this ?: def
}

fun Float?.safe(def: Float = 0f): Float {
    return this ?: def
}

fun Boolean?.safe(def: Boolean = false): Boolean {
    return this ?: def
}

fun String.sub(start: Int, end: Int): String {
    return substring(start, if (length < end) length else end)
}

fun <T> lazyNone(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T> tryCall(function: () -> T): Pair<T?, Throwable?> {
    return try {
        function() to null
    } catch (t: Throwable) {
        null to t
    }
}

fun <E> List<E>.findIndex(function: (E) -> Boolean): Int {
    for (i in 0 until size) {
        if (function(this[i])) return i
    }
    return -1
}

@Suppress("unchecked_cast")
inline fun <reified T> Any?.cast(): T? {
    if (this is T) return this
    return null
}

fun <E> List<E>.toMap(keyOf: (E) -> String): Map<String, E> {
    return hashMapOf<String, E>().also { hMap ->
        forEach {
            hMap[keyOf(it)] = it
        }
    }
}

fun Int.dp(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun Float.softTransition(compareWith: Float, allowedDiff: Float, scaleFactor: Float): Float {
    if (scaleFactor == 0f) return this //avoid from ArithmeticException (divide by zero)

    var result = this
    if (compareWith > this) {
        if (compareWith / this > allowedDiff) {
            val diff = this.coerceAtLeast(compareWith) - this.coerceAtMost(compareWith)
            result += diff / scaleFactor
        }
    } else if (this > compareWith) {
        if (this / compareWith > allowedDiff) {
            val diff = this.coerceAtLeast(compareWith) - this.coerceAtMost(compareWith)
            result -= diff / scaleFactor
        }
    }
    return result
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}
