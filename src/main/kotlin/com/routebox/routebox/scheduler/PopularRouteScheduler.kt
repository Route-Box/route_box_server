package com.routebox.routebox.scheduler

import com.routebox.routebox.application.popular_route.UpdatePopularRouteUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PopularRouteScheduler(
    private val updatePopularRouteUseCase: UpdatePopularRouteUseCase,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 매일 00:00:00에 실행
     */
    @Scheduled(cron = "0 0 0 * * *")
    fun updatePopularRoute() {
        logger.info("Scheduler is running")
        updatePopularRouteUseCase()
    }
}
