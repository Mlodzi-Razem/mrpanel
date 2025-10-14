package org.mlodzirazem.mrpanel.libraries.directory.api

class ResourceNotFoundException(val resourceName: String, val identifier: String, cause: Throwable? = null) :
    DirectoryException("Resource not found: $resourceName with identifier $identifier", cause) {
}