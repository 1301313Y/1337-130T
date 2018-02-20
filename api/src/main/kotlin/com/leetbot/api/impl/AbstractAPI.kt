package com.leetbot.api.impl

import com.leetbot.api.API
import com.leetbot.api.wrapper.Balance

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
abstract class AbstractAPI(protected val api: String, protected val secret: String): API {

    override fun balance(name: String): Balance {
        return balances().first { it.name.toLowerCase() == name.toLowerCase() }
    }
}