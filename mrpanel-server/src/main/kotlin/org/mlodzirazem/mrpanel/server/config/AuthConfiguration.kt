package org.mlodzirazem.mrpanel.server.config

import org.mlodzirazem.mrpanel.server.Profiles.GOOGLE_MOCK
import org.mlodzirazem.mrpanel.server.auth.MockUserGroupsSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AuthConfiguration {
    @Bean
    @Profile(GOOGLE_MOCK)
    fun userGroupsSource() = MockUserGroupsSource()
}