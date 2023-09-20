package team.ampersand.gmuwikiv3.common.exception

abstract class GmuwikiException(
    override val errorMessage: String,
    override val statusCode: Int
) : RuntimeException(), ExceptionAttribute {

    override fun fillInStackTrace(): Throwable {
        return this
    }
}