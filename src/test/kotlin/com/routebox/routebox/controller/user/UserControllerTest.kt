package com.routebox.routebox.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.application.user.CheckNicknameAvailabilityUseCase
import com.routebox.routebox.application.user.UpdateUserInfoUseCase
import com.routebox.routebox.application.user.dto.UpdateUserInfoCommand
import com.routebox.routebox.application.user.dto.UpdateUserInfoResult
import com.routebox.routebox.config.ControllerTestConfig
import com.routebox.routebox.controller.user.dto.UpdateUserInfoRequest
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.UserRoleType
import com.routebox.routebox.security.UserPrincipal
import org.apache.commons.lang3.RandomStringUtils
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import kotlin.random.Random
import kotlin.test.Test

@Import(ControllerTestConfig::class)
@WebMvcTest(controllers = [UserController::class])
class UserControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @MockBean
    lateinit var updateUserInfoUseCase: UpdateUserInfoUseCase

    @MockBean
    lateinit var checkNicknameAvailabilityUseCase: CheckNicknameAvailabilityUseCase

    @Test
    fun `닉네임이 주어지고, 주어진 닉네임이 이용 가능한지 확인한다`() {
        // given
        val nickname = RandomStringUtils.random(8, true, true)
        val expectedResult = true
        given(checkNicknameAvailabilityUseCase.invoke(nickname)).willReturn(expectedResult)

        // when & then
        mvc.perform(
            get("/api/v1/users/nickname/{nickname}/availability", nickname),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.result.nickname").value(nickname))
            .andExpect(jsonPath("$.result.isAvailable").value(expectedResult))
        then(checkNicknameAvailabilityUseCase).should().invoke(nickname)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `수정할 유저 정보(닉네임, 생일 등)가 주어지고, 유저 정보를 수정하면, 변경된 유저 정보가 응답된다`() {
        // given
        val userId = Random.nextLong()
        val request = UpdateUserInfoRequest(
            nickname = RandomStringUtils.random(8, true, true),
            gender = Gender.MALE,
            birthDay = LocalDate.now(),
            introduction = RandomStringUtils.random(25, true, true),
        )
        val command = UpdateUserInfoCommand(
            id = userId,
            nickname = request.nickname,
            gender = request.gender,
            birthDay = request.birthDay,
            introduction = request.introduction,
        )
        val expectedResult = UpdateUserInfoResult(
            id = userId,
            profileImageUrl = "https://user-profile-image",
            nickname = request.nickname!!,
            gender = request.gender!!,
            birthDay = request.birthDay!!,
            introduction = request.introduction!!,
            point = Random.nextInt(),
        )
        given(updateUserInfoUseCase.invoke(command)).willReturn(expectedResult)

        // when & then
        mvc.perform(
            patch("/api/v1/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .with(user(createUserPrincipal(userId))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.result.id").value(expectedResult.id))
            .andExpect(jsonPath("$.result.nickname").value(expectedResult.nickname))
            .andExpect(jsonPath("$.result.gender").value(expectedResult.gender.toString()))
            .andExpect(jsonPath("$.result.birthDay").value(expectedResult.birthDay.toString()))
        then(updateUserInfoUseCase).should().invoke(command)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(updateUserInfoUseCase).shouldHaveNoMoreInteractions()
        then(checkNicknameAvailabilityUseCase).shouldHaveNoMoreInteractions()
    }

    private fun createUserPrincipal(userId: Long) = UserPrincipal(
        userId = userId,
        socialLoginUid = userId.toString(),
        userRoles = setOf(UserRoleType.USER),
    )
}
