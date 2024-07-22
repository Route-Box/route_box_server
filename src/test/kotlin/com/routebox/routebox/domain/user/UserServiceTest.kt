package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.user.UserNicknameDuplicationException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.user.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
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

    @ValueSource(booleans = [true, false])
    @ParameterizedTest
    fun `닉네임이 주어지고, 주어진 닉네임이 이용 가능한지 확인한다`(expectedResult: Boolean) {
        // given
        val nickname = RandomStringUtils.random(8, true, true)
        given(userRepository.existsByNickname(nickname)).willReturn(!expectedResult)

        // when
        val actualResult = sut.isNicknameAvailable(nickname)

        // then
        then(userRepository).should().existsByNickname(nickname)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `유저 id와 변경하고자 하는 닉네임이 주어지고, 유저 닉네임을 변경하면, 변경된 유저 정보가 반환된다`() {
        // given
        val userId = Random.nextLong()
        val newNickname = Random.toString()
        given(userRepository.findById(userId)).willReturn(Optional.of(createUser(userId)))
        given(userRepository.existsByNickname(newNickname)).willReturn(false)

        // when
        val result = sut.updateUser(id = userId, nickname = newNickname)

        // then
        then(userRepository).should().findById(userId)
        then(userRepository).should().existsByNickname(newNickname)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.nickname).isEqualTo(newNickname)
    }

    @Test
    fun `유저 id와 변경하고자 하는 닉네임이 주어지고, 유저 닉네임을 변경한다, 만약 다른 유저가 사용중인 닉네임이라면, 예외가 발생한다`() {
        // given
        val userId = Random.nextLong()
        val nickname = Random.toString()
        given(userRepository.findById(userId)).willReturn(Optional.of(createUser(userId)))
        given(userRepository.existsByNickname(nickname)).willReturn(true)

        // when
        val ex = catchThrowable { sut.updateUser(id = userId, nickname = nickname) }

        // then
        then(userRepository).should().findById(userId)
        then(userRepository).should().existsByNickname(nickname)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(UserNicknameDuplicationException::class.java)
    }

    @Test
    fun `유저 id와 변경하고자 하는 성별이 주어지고, 유저 성별을 변경하면, 변경된 유저 정보가 반환된다`() {
        // given
        val userId = Random.nextLong()
        val newGender = Gender.MALE
        given(userRepository.findById(userId)).willReturn(Optional.of(createUser(userId)))

        // when
        val result = sut.updateUser(id = userId, gender = newGender)

        // then
        then(userRepository).should().findById(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.gender).isEqualTo(newGender)
    }

    @Test
    fun `유저 id와 변경하고자 하는 생일이 주어지고, 유저 생일을 변경하면, 변경된 유저 정보가 반환된다`() {
        // given
        val userId = Random.nextLong()
        val newBirthDay = LocalDate.now()
        given(userRepository.findById(userId)).willReturn(Optional.of(createUser(userId)))

        // when
        val result = sut.updateUser(id = userId, birthDay = newBirthDay)

        // then
        then(userRepository).should().findById(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.birthDay).isEqualTo(newBirthDay)
    }

    @Test
    fun `유저 id와 변경하고자 하는 한 줄 소개가 주어지고, 유저 한 줄 소개를 변경하면, 변경된 유저 정보가 반환된다`() {
        // given
        val userId = Random.nextLong()
        val newIntroduction = Random.toString()
        given(userRepository.findById(userId)).willReturn(Optional.of(createUser(userId)))

        // when
        val result = sut.updateUser(id = userId, introduction = newIntroduction)

        // then
        then(userRepository).should().findById(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.introduction).isEqualTo(newIntroduction)
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
