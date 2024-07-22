package com.routebox.routebox.application.user

import com.routebox.routebox.domain.user.UserService
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CheckNicknameAvailabilityUseCaseTest {
    @InjectMocks
    lateinit var sut: CheckNicknameAvailabilityUseCase

    @Mock
    lateinit var userService: UserService

    @Test
    fun `닉네임이 주어지고, 주어진 닉네임이 이용 가능한지 확인한다`() {
        // given
        val nickname = RandomStringUtils.random(8, true, true)
        val expectedResult = true
        given(userService.isNicknameAvailable(nickname)).willReturn(expectedResult)

        // when
        val actualResult = sut.invoke(nickname)

        // then
        then(userService).should().isNicknameAvailable(nickname)
        then(userService).shouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
