package com.routebox.routebox.controller.route.dto

data class SearchFilters(
    val whoWith: List<String>,

    val numberOfPeople: List<Int>,

    val numberOfDays: List<String>,

    val style: List<String>,

    val transportation: List<String>,
) {
    companion object {
        fun from(whoWith: List<String>, numberOfPeople: List<Int>, numberOfDays: List<String>, style: List<String>, transportation: List<String>): SearchFilters = SearchFilters(
            whoWith = whoWith,
            numberOfPeople = numberOfPeople,
            numberOfDays = numberOfDays,
            style = style,
            transportation = transportation,
        )
    }
}
