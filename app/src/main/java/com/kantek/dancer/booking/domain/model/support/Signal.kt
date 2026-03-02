package com.kantek.dancer.booking.domain.model.support

interface Signal : Emittable, Subscriber

interface Emittable {
    fun emit()
}

interface Subscriber {
    fun subscribe(callback: () -> Unit): AutoCloseable
}

fun signal(): Signal {
    return object : Signal {
        private val mObservers = arrayListOf<() -> Unit>()

        override fun emit() {
            synchronized(mObservers) { mObservers.forEach { it() } }
        }

        override fun subscribe(callback: () -> Unit): AutoCloseable {
            synchronized(mObservers) { mObservers.add(callback) }
            return AutoCloseable { synchronized(mObservers) { mObservers.remove(callback) } }
        }
    }
}