package com.kantek.dancer.booking.domain.model.ui.search

interface IFilter {
    val id: Int get() = 0
    val name: String get() = ""
}

interface IState : IFilter

interface ICity : IFilter

interface ISpeciality : IFilter