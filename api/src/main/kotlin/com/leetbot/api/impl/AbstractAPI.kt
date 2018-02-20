package com.leetbot.api.impl

import com.leetbot.api.API

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
abstract class AbstractAPI(protected val api: String, protected val secret: String): API {

}