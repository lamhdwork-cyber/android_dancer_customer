package com.kantek.dancer.booking.domain.model.support

import com.kantek.dancer.booking.presentation.extensions.edit

interface HasIsValid {
    val isValid: Boolean
}

object NoValidable : Validable {
    override fun validate(): Boolean {
        return true
    }
}

class NotBlankValidable : Validable, Updatable {
    private var mValue: String? = null

    override fun update(value: Any?, notify: Boolean) {
        mValue = value?.toString()
    }

    override fun validate(): Boolean {
        return !mValue.isNullOrBlank()
    }
}

class ValidableDelegate(def: Validable = NoValidable) : Validable, HasIsValid, Updatable {
    var delegate: Validable = def

    override fun validate(): Boolean {
        return delegate.validate().also { isValid = it }
    }

    override fun update(value: Any?, notify: Boolean) {
        delegate.edit()?.update(value, notify)
    }

    override var isValid: Boolean = true

}

interface Validable {
    fun validate(): Boolean
}

interface HasValidable {
    val validable: Validable
}