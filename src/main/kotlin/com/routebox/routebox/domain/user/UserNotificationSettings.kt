package com.routebox.routebox.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class UserNotificationSettings(
    val enableReceivingMarketingInfo: Boolean = true,
    val enableReceivingTravelPhoto: Boolean = true,
)
