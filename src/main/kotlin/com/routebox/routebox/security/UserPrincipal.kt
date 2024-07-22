package com.routebox.routebox.security

import com.routebox.routebox.domain.user.constant.UserRoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    val userId: Long,
    val socialLoginUid: String,
    val userRoles: Collection<UserRoleType>,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        userRoles.map { userRole -> userRole.name }
            .map { userRoleName -> SimpleGrantedAuthority(userRoleName) }
            .toList()

    override fun getUsername(): String = userId.toString()

    override fun getPassword(): String = socialLoginUid
}
