package team.ampersand.gmuwikiv3.global.error

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import team.ampersand.gmuwikiv3.common.exception.ExceptionAttribute
import team.ampersand.gmuwikiv3.common.exception.GmuwikiException
import team.ampersand.gmuwikiv3.global.error.exception.InternalServerErrorException
import team.ampersand.gmuwikiv3.global.error.exception.NotFoundRequestHandlerException

@Order(-2)
@Component
class GlobalExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext
){

    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), this::handleError)

    private fun handleError(request: ServerRequest): Mono<ServerResponse> =
        when (val e = super.getError(request)) {
            is GmuwikiException -> e.toErrorResponse()
            is WebExchangeBindException -> e.getBindErrorMessage()
            is ResponseStatusException -> NotFoundRequestHandlerException(GlobalErrorCode.NOT_FOUND_REQUEST_HANDLER.errorMessage).toErrorResponse()
            else -> {
                e.printStackTrace()
                InternalServerErrorException(GlobalErrorCode.INTERNAL_SERVER_ERROR.errorMessage).toErrorResponse()
            }
        }

    private fun BindingResult.getBindErrorMessage(): Mono<ServerResponse> {
        val errorMap = HashMap<String, String?>()

        for (error: FieldError in this.fieldErrors) {
            errorMap[error.field] = error.defaultMessage
        }

        val errorStatus = HttpStatus.BAD_REQUEST

        return ServerResponse.status(errorStatus)
            .bodyValue(
                BindingErrorResponse(
                    statusCode = errorStatus.value(),
                    fieldError = errorMap,
                )
            )
    }

    private fun ExceptionAttribute.toErrorResponse() =
        ServerResponse.status(this.statusCode)
            .bodyValue(
                ErrorResponse(
                    message  = this.errorMessage,
                    statusCode = this.statusCode,
                ),
            )
}

data class BindingErrorResponse(
    val statusCode: Int,
    val fieldError: Map<String, String?>
)

data class ErrorResponse(
    val message: String,
    val statusCode: Int
)

