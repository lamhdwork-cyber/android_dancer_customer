package com.kantek.dancer.booking.domain.model.ui

interface IChars : CharSequence {
    override val length: Int
        get() = toString().length

    override fun get(index: Int): Char {
        return toString()[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return toString().subSequence(startIndex, endIndex)
    }
}