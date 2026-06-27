package com.hdl.dancer.booking.domain.model.search

interface IFilter {
    val id: Int get() = 0
    val name: String get() = ""
}

interface IState : IFilter

interface ICity : IFilter

interface ISpeciality : IFilter