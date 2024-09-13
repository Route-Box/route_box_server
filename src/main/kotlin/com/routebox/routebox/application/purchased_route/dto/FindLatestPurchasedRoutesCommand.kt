package com.routebox.routebox.application.purchased_route.dto

data class FindLatestPurchasedRoutesCommand(
    val requesterId: Long,
    val page: Int,
    val pageSize: Int,
)
