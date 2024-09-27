package com.routebox.routebox.domain.purchased_route

import com.routebox.routebox.exception.purchased_route.PurchasedRouteNotFoundException
import com.routebox.routebox.infrastructure.purchased_route.PurchasedRouteRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PurchasedRouteService(
    private val purchasedRouteRepository: PurchasedRouteRepository,
) {
    @Transactional(readOnly = true)
    fun findLatestPurchasedRoutesByBuyer(buyerId: Long, page: Int, pageSize: Int): Page<PurchasedRoute> =
        purchasedRouteRepository.findByBuyer_IdOrderByCreatedAtDesc(buyerId, PageRequest.of(page, pageSize))

    @Transactional(readOnly = true)
    fun getPurchasedRouteById(purchasedRouteId: Long): PurchasedRoute =
        purchasedRouteRepository.findById(purchasedRouteId).orElseThrow { PurchasedRouteNotFoundException() }

    @Transactional
    fun createPurchasedRoute(purchasedRoute: PurchasedRoute) {
        purchasedRouteRepository.save(purchasedRoute)
    }
}
