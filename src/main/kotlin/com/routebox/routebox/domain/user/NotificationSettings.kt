package com.routebox.routebox.domain.user

import jakarta.persistence.Embeddable

@Embeddable
class NotificationSettings(
    enableReceivingMarketingInfo: Boolean = true,
    enableReceivingTravelPhoto: Boolean = true,
) {
    var enableReceivingMarketingInfo: Boolean = enableReceivingMarketingInfo
        private set

    var enableReceivingTravelPhoto: Boolean = enableReceivingTravelPhoto
        private set
}
