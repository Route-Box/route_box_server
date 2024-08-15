package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.UpdateUserInfoCommand
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class UpdateUserInfoUseCaseTest {
    @InjectMocks
    lateinit var sut: UpdateUserInfoUseCase

    @Mock
    lateinit var userService: UserService

    @Test
    fun `수정할 유저 정보(닉네임, 생일 등)가 주어지고, 유저 정보를 수정하면, 변경된 유저 정보가 반환된다`() {
        // given
        val command = UpdateUserInfoCommand(
            id = Random.nextLong(),
            nickname = RandomStringUtils.random(8, true, true),
            gender = Gender.MALE,
            birthDay = LocalDate.now(),
            introduction = RandomStringUtils.random(25, true, true),
            profileImage = createMockImageFile(),
        )
        val expectedResult = createUser(
            id = command.id,
            nickname = command.nickname!!,
            gender = command.gender!!,
            birthDay = command.birthDay!!,
            introduction = command.introduction!!,
        )
        given(
            userService.updateUser(
                id = command.id,
                nickname = command.nickname,
                gender = command.gender,
                birthDay = command.birthDay,
                introduction = command.introduction,
                profileImage = command.profileImage,
            ),
        ).willReturn(expectedResult)

        // when
        val actualResult = sut.invoke(command)

        // then
        then(userService).should().updateUser(
            command.id,
            command.nickname,
            command.gender,
            command.birthDay,
            command.introduction,
            command.profileImage,
        )
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult.id).isEqualTo(expectedResult.id)
        assertThat(actualResult.nickname).isEqualTo(expectedResult.nickname)
        assertThat(actualResult.gender).isEqualTo(expectedResult.gender)
        assertThat(actualResult.birthDay).isEqualTo(expectedResult.birthDay)
        assertThat(actualResult.introduction).isEqualTo(expectedResult.introduction)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(userService).shouldHaveNoMoreInteractions()
    }

    private fun createMockImageFile() = MockMultipartFile(
        "file",
        "newImage.jpg",
        "image/jpeg",
        "new image content".toByteArray(),
    )

    private fun createUser(
        id: Long,
        nickname: String,
        gender: Gender,
        birthDay: LocalDate,
        introduction: String,
    ) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = Random.nextInt().toString(),
        nickname = nickname,
        gender = gender,
        birthDay = birthDay,
        introduction = introduction,
    )
}
