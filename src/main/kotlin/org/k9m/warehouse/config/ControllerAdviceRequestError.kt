package org.k9m.warehouse.config

import org.k9m.warehouse.api.model.ErrorObjectDto
import org.k9m.warehouse.service.exception.ArticleNotFoundException
import org.k9m.warehouse.service.exception.NoStockException
import org.k9m.warehouse.service.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ArticleNotFoundException::class, ProductNotFoundException::class])
    fun handleNotFound(ex: Exception, request: WebRequest): ResponseEntity<ErrorObjectDto> {
        val status = HttpStatus.NOT_FOUND
        val errorDetails = ErrorObjectDto(
            ex.message!!,
            status.value()
        )

        return ResponseEntity(errorDetails, status)
    }

    @ExceptionHandler(value = [NoStockException::class])
    fun handleNoStock(ex: Exception, request: WebRequest): ResponseEntity<ErrorObjectDto> {
        val status = HttpStatus.EXPECTATION_FAILED
        val errorDetails = ErrorObjectDto(
            ex.message!!,
            status.value()
        )

        return ResponseEntity(errorDetails, status)
    }
}