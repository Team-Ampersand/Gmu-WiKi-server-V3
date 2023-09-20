package team.ampersand.gmuwikiv3.global.error.exception

import team.ampersand.gmuwikiv3.common.exception.GmuwikiException

class UnAuthorizedException(
    errorMessage: String
) : GmuwikiException(errorMessage, 401)