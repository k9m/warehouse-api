package org.k9m.warehouse.util

import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.io.InputStreamReader
import java.io.UncheckedIOException
import java.nio.charset.StandardCharsets


class FileUtil {

    fun readFileToString(path: String): String {
        val resourceLoader: ResourceLoader = DefaultResourceLoader()
        val resource = resourceLoader.getResource(path)
        return asString(resource)
    }

    private fun asString(resource: Resource): String {
        try {
            return InputStreamReader(resource.inputStream, StandardCharsets.UTF_8).use { reader -> FileCopyUtils.copyToString(reader) }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

}