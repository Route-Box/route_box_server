package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.UpdateUserInfoCommand
import com.routebox.routebox.application.user.dto.UpdateUserInfoResult
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.user.UserNicknameDuplicationException
import org.springframework.stereotype.Component

@Component
class UpdateUserInfoUseCase(
    private val userService: UserService,
) {
    /**
     * 유저 정보 수정.
     * `null`이 아닌 값으로 전달된 항목들에 대해서만 수정이 진행된다.
     *
     * @param command user id, 변경하려고 하는 유저 정보
     * @return 변경된 유저 정보
     * @throws UserNicknameDuplicationException 변경하려고 하는 닉네임이 이미 사용중인 경우
     */
    operator fun invoke(command: UpdateUserInfoCommand): UpdateUserInfoResult {
        val updatedUser = userService.updateUser(
            id = command.id,
            nickname = command.nickname,
            gender = command.gender,
            birthDay = command.birthDay,
            introduction = command.introduction,
        )
        return UpdateUserInfoResult.from(updatedUser)
    }
}
