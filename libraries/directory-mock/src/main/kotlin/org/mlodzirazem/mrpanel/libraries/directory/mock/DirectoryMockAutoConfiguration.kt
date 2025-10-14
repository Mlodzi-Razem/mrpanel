package org.mlodzirazem.mrpanel.libraries.directory.mock

import org.mlodzirazem.mrpanel.libraries.directory.api.group.GroupSource
import org.mlodzirazem.mrpanel.libraries.directory.api.user.UserSource
import org.mlodzirazem.mrpanel.libraries.directory.mock.group.MockGroupSource
import org.mlodzirazem.mrpanel.libraries.directory.mock.model.DirectoryMock
import org.mlodzirazem.mrpanel.libraries.directory.mock.user.MockUserSource
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@AutoConfiguration
@ConditionalOnProperty("mrpanel.directory.provider", havingValue = "mock")
@Import(DirectoryMockLoader::class)
class DirectoryMockAutoConfiguration {
    @Bean
    fun directoryMock(directoryMockLoader: DirectoryMockLoader): DirectoryMock {
        return directoryMockLoader.directoryMock;
    }

    @Bean
    fun userSource(directoryMock: DirectoryMock): UserSource {
        return MockUserSource(directoryMock)
    }

    @Bean
    fun groupSource(directoryMock: DirectoryMock): GroupSource {
        return MockGroupSource(directoryMock)
    }
}