package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.user.UserNicknameDuplicationException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.time.LocalDate
import java.util.Optional
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @InjectMocks
    private lateinit var sut: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Test
    fun `id가 주어지고, 주어진 id로 유저를 조회한다`() {
        // given
        val id = Random.nextLong()
        val expectedResult = createUser(id)
        given(userRepository.findById(id)).willReturn(Optional.of(expectedResult))

        // when
        val actualResult = sut.getUserById(id)

        // then
        then(userRepository).should().findById(id)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `존재하지 않는 유저의 id로 일치하는 유저를 조회하면, 예외가 발생한다`() {
        // given
        val id = Random.nextLong()
        given(userRepository.findById(id)).willReturn(Optional.empty())

        // when
        val ex = catchThrowable { sut.getUserById(id) }
        // then
        then(userRepository).should().findById(id)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun `Social login uid가 주어지고, 주어진 uid로 유저를 조회한다`() {
        // given
        val socialLoginUid = Random.toString()
        val expectedResult = createUser(id = Random.nextLong())
        given(userRepository.findBySocialLoginUid(socialLoginUid)).willReturn(expectedResult)

        // when
        val actualResult = sut.getUserBySocialLoginUid(socialLoginUid)

        // then
        then(userRepository).should().findBySocialLoginUid(socialLoginUid)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `존재하지 않는 유저의 social login uid로 일치하는 유저를 조회하면, 예외가 발생한다`() {
        // given
        val socialLoginUid = Random.toString()
        given(userRepository.findBySocialLoginUid(socialLoginUid)).willReturn(null)

        // when
        val ex = catchThrowable { sut.getUserBySocialLoginUid(socialLoginUid) }
        // then
        then(userRepository).should().findBySocialLoginUid(socialLoginUid)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun `신규 유저 데이터를 생성 및 저장한다`() {
        // given
        val socialLoginUid = Random.toString()
        val expectedResult = createUser(id = Random.nextLong())
        given(userRepository.existsBySocialLoginUid(socialLoginUid)).willReturn(false)
        given(userRepository.existsByNickname(anyString())).willReturn(false)
        given(userRepository.save(any(User::class.java))).willReturn(expectedResult)

        // when
        val actualResult = sut.createNewUser(loginType = LoginType.KAKAO, socialLoginUid = socialLoginUid)

        // then
        then(userRepository).should().existsBySocialLoginUid(socialLoginUid)
        then(userRepository).should().existsByNickname(anyString())
        then(userRepository).should().save(any(User::class.java))
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `기존 유저의 social login uid로 신규 유저 데이터를 생성 및 저장하려고 하면, 예외가 발생한다`() {
        // given
        val socialLoginUid = Random.toString()
        given(userRepository.existsBySocialLoginUid(socialLoginUid)).willReturn(true)

        // when
        val ex = catchThrowable { sut.createNewUser(loginType = LoginType.KAKAO, socialLoginUid = socialLoginUid) }

        // then
        then(userRepository).should().existsBySocialLoginUid(socialLoginUid)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(UserSocialLoginUidDuplicationException::class.java)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(userRepository).shouldHaveNoMoreInteractions()
    }

    private fun createUser(id: Long) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = Random.toString(),
        nickname = Random.toString(),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )
}
