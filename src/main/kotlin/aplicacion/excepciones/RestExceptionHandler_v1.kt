package aplicacion.excepciones

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: Exception): ResponseEntity<ErrorInfo> {
        val status = HttpStatus.NOT_FOUND
        return ResponseEntity(ErrorInfo(status, ex), status)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: Exception): ResponseEntity<ErrorInfo> {
        val status = HttpStatus.UNPROCESSABLE_ENTITY
        return ResponseEntity(ErrorInfo(status, ex), status)
    }

    @ExceptionHandler(CarritoExcepcion::class)
    fun handleCarritoException(ex: Exception): ResponseEntity<ErrorInfo> {
        val status = HttpStatus.UNAUTHORIZED
        return ResponseEntity(ErrorInfo(status, ex), status)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: Exception): ResponseEntity<ErrorInfo> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity(ErrorInfo(status, ex), status)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorInfo> {
        val status = HttpStatus.BAD_REQUEST
        val mensaje = "${ex.name} debe ser de tipo ${ex.requiredType?.name}"
        return ResponseEntity(ErrorInfo(status, mensaje), status)
    }

    @JsonPropertyOrder(value = ["status", "mensaje", "statusCode"])
    data class ErrorInfo(
        var status: Boolean,
        var mensaje: String,
        var statusCode: Int,
    ) {
        constructor(statusCode: HttpStatus, exception: Exception) : this(
            false, exception.localizedMessage, statusCode.value()
        )

        constructor(statusCode: HttpStatus, exception: String) : this(
            false, exception, statusCode.value()
        )
    }
}