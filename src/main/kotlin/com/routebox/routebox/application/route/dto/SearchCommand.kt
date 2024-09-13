package com.routebox.routebox.application.route.dto

import com.routebox.routebox.controller.route.dto.RouteSortBy
import com.routebox.routebox.controller.route.dto.SearchFilters

data class SearchCommand(
    val page: Int,
    val size: Int,
    val filters: SearchFilters,
    val query: String?,
    val sortBy: RouteSortBy,
)
