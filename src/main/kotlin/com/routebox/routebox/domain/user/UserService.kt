package com.routebox.routebox.domain.user

import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.user.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UserService(private val userRepository: UserRepository) {
    /**
     * Id(PK)로 유저를 조회한다.
     *
     * @param id 조회할 유저의 id
     * @return 조회된 user entity
     * @throws UserNotFoundException 주어진 id와 일치하는 유저가 없는 경우
     */
    @Transactional(readOnly = true)
    fun getUserById(id: Long): User =
        userRepository.findById(id).orElseThrow { UserNotFoundException() }

    /**
     * Social login uid로 유저를 조회한다.
     *
     * @param socialLoginUid 조회할 유저의 social login uid
     * @return 조회된 user entity
     * * @throws UserNotFoundException 주어진 social login uid와 일치하는 유저가 없는 경우
     */
    @Transactional(readOnly = true)
    fun getUserBySocialLoginUid(socialLoginUid: String): User =
        userRepository.findBySocialLoginUid(socialLoginUid) ?: throw UserNotFoundException()

    /**
     * 신규 유저 데이터를 생성 및 저장한다.
     *
     * @param loginType 유저가 사용한 로그인 종류
     * @param socialLoginUid 유저의 social login uid
     * @return 생성된 신규 유저 데이터
     * @throws UserSocialLoginUidDuplicationException 이미 가입된 유저인 경우
     */
    @Transactional
    fun createNewUser(loginType: LoginType, socialLoginUid: String): User {
        if (userRepository.existsBySocialLoginUid(socialLoginUid)) {
            throw UserSocialLoginUidDuplicationException()
        }
        return userRepository.save(
            User(
                loginType = loginType,
                socialLoginUid = socialLoginUid,
                nickname = generateUniqueNickname(),
                gender = Gender.PRIVATE,
                birthDay = LocalDate.of(1, 1, 1),
            ),
        )
    }

    /**
     * 기존 유저들이 닉네임으로 사용중이지 않은, 랜덤하고 유니크한 8글자 문자열을 생성한다.
     *
     * @return 생성된 random, unique string
     */
    private fun generateUniqueNickname(): String {
        var name: String
        do {
            name = RandomStringUtils.random(8, true, true)
        } while (userRepository.existsByNickname(nickname = name))
        return name
    }
}