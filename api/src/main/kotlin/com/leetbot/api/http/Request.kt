package com.leetbot.api.http

/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
interface Request {

    fun open(): Request

    fun read(): Request
}