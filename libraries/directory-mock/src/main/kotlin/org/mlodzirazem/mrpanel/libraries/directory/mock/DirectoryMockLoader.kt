package org.mlodzirazem.mrpanel.libraries.directory.mock

import jakarta.xml.bind.JAXBContext
import org.mlodzirazem.mrpanel.libraries.directory.mock.model.DirectoryMock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource

class DirectoryMockLoader(
    @param:Value("\${mrpanel.directory.mock.file:classpath:default-mock.xml}")
    private val mockResource: Resource
) {
    companion object {
        val log = LoggerFactory.getLogger(DirectoryMockLoader::class.java)!!
    }

    private val parsedMock = StableValue.supplier {
        log.info("Parsing directory mock from {}", mockResource.uri)
        val jaxbContext = JAXBContext.newInstance(DirectoryMock::class.java)
        val result = jaxbContext.createUnmarshaller().unmarshal(mockResource.inputStream) as DirectoryMock
        log.info("Directory mock parsed")

        result
    }

    val directoryMock: DirectoryMock get() = parsedMock.get()
}