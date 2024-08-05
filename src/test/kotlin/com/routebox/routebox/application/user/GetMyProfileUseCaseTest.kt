package com.routebox.routebox.application.user

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
import java.time.LocalDate
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class GetMyProfileUseCaseTest {

    @InjectMocks
    lateinit var sut: GetMyProfileUseCase

    @Mock
    lateinit var userService: UserService

    @Test
    fun `유저 프로필 정보를 조회한다`() {
        // given
        val userId = Random.nextLong()
        val expectedResult = createUser(id = userId)
        given(userService.getUserById(userId)).willReturn(expectedResult)

        // when
        val actualResult = sut.invoke(userId)

        // then
        then(userService).should().getUserById(userId)
        then(userService).shouldHaveNoMoreInteractions()
        assertThat(actualResult)
            .hasFieldOrPropertyWithValue("id", expectedResult.id)
            .hasFieldOrPropertyWithValue("profileImageUrl", expectedResult.profileImageUrl)
            .hasFieldOrPropertyWithValue("nickname", expectedResult.nickname)
            .hasFieldOrPropertyWithValue("gender", expectedResult.gender)
            .hasFieldOrPropertyWithValue("birthDay", expectedResult.birthDay)
            .hasFieldOrPropertyWithValue("introduction", expectedResult.introduction)
    }

    private fun createUser(id: Long) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = RandomStringUtils.random(10),
        nickname = RandomStringUtils.random(5),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )
}
