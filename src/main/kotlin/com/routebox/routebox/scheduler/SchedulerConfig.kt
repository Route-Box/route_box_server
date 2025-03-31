package com.routebox.routebox.scheduler

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@ConditionalOnProperty(
    name = ["routebox.scheduler.enabled"],
    havingValue = "true",
    matchIfMissing = true,
)
class SchedulerConfig
