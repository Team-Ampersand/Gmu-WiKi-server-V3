package team.ampersand.gmuwikiv3.common.exception

interface ExceptionAttribute {
    val errorMessage: String
    val statusCode: Int
}