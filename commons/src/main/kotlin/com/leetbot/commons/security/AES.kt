package com.leetbot.commons.security

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
/**
 * Code written by P. Gajland
 * https://github.com/GaPhil
 */

import java.security.Key
import javax.crypto.Cipher
import sun.misc.BASE64Encoder
import sun.misc.BASE64Decoder
import javax.crypto.spec.SecretKeySpec

object AES {

    private val ALGO = "AES"
    private val keyValue = byteArrayOf('T'.toByte(), 'h'.toByte(),
            'e'.toByte(), 'B'.toByte(), 'e'.toByte(), 's'.toByte(),
            't'.toByte(), 'S'.toByte(), 'e'.toByte(), 'c'.toByte(),
            'r'.toByte(), 'e'.toByte(), 't'.toByte(), 'K'.toByte(),
            'e'.toByte(), 'y'.toByte())

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    @Throws(Exception::class)
    fun encrypt(data: String): String {
        if(data.isBlank())
            return ""
        val key = generateKey()
        val c = Cipher.getInstance(ALGO)
        c.init(Cipher.ENCRYPT_MODE, key)
        val encVal = c.doFinal(data.toByteArray())
        return BASE64Encoder().encode(encVal)
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    @Throws(Exception::class)
    fun decrypt(encryptedData: String): String {
        if(encryptedData.isBlank())
            return ""
        val key = generateKey()
        val c = Cipher.getInstance(ALGO)
        c.init(Cipher.DECRYPT_MODE, key)
        val decoded = BASE64Decoder().decodeBuffer(encryptedData)
        val decValue = c.doFinal(decoded)
        return String(decValue)
    }

    /**
     * Generate a new encryption key.
     */
    @Throws(Exception::class)
    private fun generateKey(): Key {
        return SecretKeySpec(keyValue, ALGO)
    }
}