package com.routebox.routebox.controller.route

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.application.route.GetLatestRoutesUseCase
import com.routebox.routebox.application.route.GetRouteDetailUseCase
import com.routebox.routebox.application.route.GetRouteDetailWithActivitiesUseCase
import com.routebox.routebox.application.route.PurchaseRouteUseCase
import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.config.ControllerTestConfig
import com.routebox.routebox.domain.user.constant.UserRoleType
import com.routebox.routebox.security.UserPrincipal
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@Import(ControllerTestConfig::class)
@WebMvcTest(controllers = [RouteController::class])
class RouteControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @MockBean
    lateinit var getLatestRoutesUseCase: GetLatestRoutesUseCase

    @MockBean
    lateinit var getRouteDetailUseCase: GetRouteDetailUseCase

    @MockBean
    lateinit var getRouteDetailWithActivitiesUseCase: GetRouteDetailWithActivitiesUseCase

    @MockBean
    lateinit var purchaseRouteUseCase: PurchaseRouteUseCase

    @Test
    fun `최신순으로 정렬된 루트 목록을 반환한다`() {
        // given
        val route1 = GetRouteDetailResult(
            routeId = Random.nextLong(),
            userId = Random.nextLong(),
            nickname = "User1",
            profileImageUrl = "https://example.com/image1",
            routeName = "Route 1",
            routeDescription = "Description 1",
            routeImageUrls = listOf(),
            isPurchased = false,
            purchaseCount = 0,
            commentCount = 0,
            routeStyles = listOf("Hiking"),
            whoWith = "Friend",
            transportation = "Car",
            numberOfPeople = 2,
            numberOfDays = "3 days",
            createdAt = LocalDateTime.now(),
        )

        val route2 = GetRouteDetailResult(
            routeId = Random.nextLong(),
            userId = Random.nextLong(),
            nickname = "User2",
            profileImageUrl = "https://example.com/image2",
            routeName = "Route 2",
            routeDescription = "Description 2",
            routeImageUrls = listOf(),
            isPurchased = false,
            purchaseCount = 0,
            commentCount = 0,
            routeStyles = listOf("Sightseeing"),
            whoWith = "Friend",
            transportation = "Car",
            numberOfPeople = 2,
            numberOfDays = "3 days",
            createdAt = LocalDateTime.now().minusDays(1),
        )

        val page = 0
        val size = 10
        val userId = Random.nextLong()

        val expectedResult = listOf(route1, route2)
        given(getLatestRoutesUseCase.invoke(page, size)).willReturn(expectedResult)

        // when & then
        mvc.perform(
            get("/api/v1/routes")
                .param("page", page.toString())
                .param("size", size.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(createUserPrincipal(userId))),
        ).andExpect(status().isOk)

        then(getLatestRoutesUseCase).should().invoke(page, size)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `입력받은 루트 id에 해당하는 루트를 반환한다`() {
        // given
        val routeId = Random.nextLong()
        val route = GetRouteDetailResult(
            routeId = routeId,
            userId = Random.nextLong(),
            nickname = "User1",
            profileImageUrl = "https://example.com/image1",
            routeName = "Route 1",
            routeDescription = "Description 1",
            routeImageUrls = listOf(),
            isPurchased = false,
            purchaseCount = 0,
            commentCount = 0,
            routeStyles = listOf("Hiking"),
            whoWith = "Friend",
            transportation = "Car",
            numberOfPeople = 2,
            numberOfDays = "3 days",
            createdAt = LocalDateTime.now(),
        )

        given(getRouteDetailUseCase(routeId)).willReturn(route)

        // when & then
        mvc.perform(
            get("/api/v1/routes/{routeId}", routeId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(createUserPrincipal(routeId))),
        ).andExpect(status().isOk)

        then(getRouteDetailUseCase).should().invoke(routeId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(getLatestRoutesUseCase).shouldHaveNoMoreInteractions()
        then(getRouteDetailUseCase).shouldHaveNoMoreInteractions()
        then(getRouteDetailWithActivitiesUseCase).shouldHaveNoMoreInteractions()
        then(purchaseRouteUseCase).shouldHaveNoMoreInteractions()
    }

    private fun createUserPrincipal(userId: Long) = UserPrincipal(
        userId = userId,
        socialLoginUid = userId.toString(),
        userRoles = setOf(UserRoleType.USER),
    )
}
