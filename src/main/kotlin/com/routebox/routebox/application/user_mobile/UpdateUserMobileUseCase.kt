package com.routebox.routebox.application.user_mobile

import com.routebox.routebox.application.user_mobile.dto.UpdateUserMobileCommand
import com.routebox.routebox.domain.user_mobile.UserMobile
import com.routebox.routebox.domain.user_mobile.UserMobileService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UpdateUserMobileUseCase(
    private val userMobileService: UserMobileService,
) {
    @Transactional
    operator fun invoke(command: UpdateUserMobileCommand): Long {
        // user 푸시 이력 조회
        val userMobile = userMobileService.get(command.userId)

        // 이미 있으면 수정
        if (userMobile !== null) {
            userMobile.update(command.pushToken, command.os)
            userMobileService.create(userMobile)
            return userMobile.id
        } else {
            // 없으면 새로 추가
            val newMobile = UserMobile(
                userId = command.userId,
                pushToken = command.pushToken,
                osType = command.os,
            )

            val userMobileId = userMobileService.create(newMobile)
            return userMobileId
        }
    }
}
