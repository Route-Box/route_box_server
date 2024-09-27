package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.user.UserNicknameDuplicationException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.user.UserProfileImageRepository
import com.routebox.routebox.infrastructure.user.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userProfileImageRepository: UserProfileImageRepository,
    private val fileManager: FileManager,
) {
    companion object {
        const val USER_PROFILE_IMAGE_UPLOAD_PATH = "user-profile-images/"
    }

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
     * Id(PK)로 유저를 조회한다.
     *
     * @param id 조회할 유저의 id
     * @return 조회된 user entity(nullable)
     */
    @Transactional(readOnly = true)
    fun findUserById(id: Long): User? =
        userRepository.findById(id).getOrNull()

    /**
     * Social login uid로 유저를 조회한다.
     *
     * @param socialLoginUid 조회할 유저의 social login uid
     * @return 조회된 user entity (nullable)
     */
    @Transactional(readOnly = true)
    fun findUserBySocialLoginUid(socialLoginUid: String): User? =
        userRepository.findBySocialLoginUid(socialLoginUid)

    /**
     * 닉네임이 이용 가능한지 조회한다.
     *
     * @param nickname 이용 가능 여부를 확인할 닉네임
     * @return 닉네임의 이용 가능 여부. `true`인 경우 이용 가능한 닉네임.
     */
    @Transactional(readOnly = true)
    fun isNicknameAvailable(nickname: String): Boolean =
        !userRepository.existsByNickname(nickname)

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
     * 유저 정보 수정.
     * `null`이 아닌 값으로 전달된 항목들에 대해서만 수정이 진행된다.
     *
     * @param id id of user
     * @param nickname 설정하고자 하는 닉네임
     * @param gender 설정하고자 하는 성별
     * @param birthDay 설정하고자 하는 생일
     * @param introduction 설정하고자 하는 한 줄 소개
     * @return 변경된 user entity
     * @throws UserNicknameDuplicationException 변경하려고 하는 닉네임이 이미 사용중인 경우
     */
    @Transactional
    fun updateUser(
        id: Long,
        nickname: String? = null,
        gender: Gender? = null,
        birthDay: LocalDate? = null,
        introduction: String? = null,
        profileImage: MultipartFile? = null,
    ): User {
        val user = getUserById(id)

        nickname?.let { newNickname ->
            if (userRepository.existsByNickname(newNickname)) {
                throw UserNicknameDuplicationException()
            }
            user.updateNickname(newNickname)
        }
        gender?.let { user.updateGender(it) }
        birthDay?.let { user.updateBirthDay(it) }
        introduction?.let { user.updateIntroduction(it) }
        profileImage?.let { image ->
            val currentUserProfileImage = userProfileImageRepository.findByUserId(userId = id)
            currentUserProfileImage?.delete()

            val (profileImageName, profileImageUrl) = fileManager.upload(image, USER_PROFILE_IMAGE_UPLOAD_PATH)
            userProfileImageRepository.save(
                UserProfileImage(
                    userId = id,
                    storedFileName = profileImageName,
                    fileUrl = profileImageUrl,
                ),
            )
            user.updateProfileImageUrl(profileImageUrl)
        }

        return user
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
