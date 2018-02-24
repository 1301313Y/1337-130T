package com.leetbot.api.impl.binance.http

import com.leetbot.api.http.Request
import com.leetbot.api.throwable.APIException
import com.leetbot.commons.utils.BotLogger
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.apache.commons.codec.binary.Hex
import org.apache.commons.io.IOUtils
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.HttpURLConnection
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import java.io.*
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec




/**
 * ${FILE_NAME}
 *
 * @author 1301313Y
 * @since 2/19/2018
 * @version 1.0.0
 */
class BinanceRequest(private var request: String): Request {

    enum class RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    companion object {
        private val parser = JSONParser()
        var userAgent = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0"
    }

    var connection: HttpURLConnection? = null
    val properties: HashMap<String, String> = HashMap()
    var method: RequestMethod = RequestMethod.GET
    var lastResponse = ""
    private var requestBody = ""

    override fun open(): BinanceRequest {

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

            }

            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        })
        val url: URL
        try {
            url = URL(request)
            BotLogger.debug("%s %s".format(method, url))
        } catch (e: MalformedURLException) {
            throw APIException("Malformed URL Was Given!", e)
        }

        val sc: SSLContext
        try {
            sc = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        } catch (e: NoSuchAlgorithmException) {
            throw APIException("SSL Error", e)
        } catch (e: KeyManagementException) {
            throw APIException("Key Management Error Occurred!", e)
        }


        try {
            connection = url.openConnection() as HttpsURLConnection
        } catch (e: IOException) {
            throw APIException("HTTPS Connection Error Occurred!", e)
        }


        try {
            connection!!.requestMethod = method.toString()
        } catch (e: ProtocolException) {
            throw APIException("HTTP Method Error Occurred", e)
        }

        connection!!.setRequestProperty("User-Agent", userAgent)
        properties.forEach { t, u ->  connection!!.setRequestProperty(t, u)}
        return this
    }

    override fun read(): BinanceRequest {
        if (connection == null) {
            open()
        }
        try {
            // posting payload it we do not have it yet
            if (requestBody.isNotEmpty()) {
                BotLogger.debug("Payload: %s".format(requestBody))
                connection!!.doInput = true
                connection!!.doOutput = true
                val writer = OutputStreamWriter(connection!!.outputStream, "UTF-8")
                writer.write(requestBody)
                writer.close()
            }

            val inputStream: InputStream
            if (connection!!.responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection!!.inputStream
            } else {
                /* error from server */
                inputStream = connection!!.errorStream
            }

            val br = BufferedReader(InputStreamReader(inputStream))
            lastResponse = IOUtils.toString(br)
            BotLogger.debug("Response: %s".format(lastResponse))

            if (connection!!.responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                // Try to parse JSON
                val obj = parser.parse(lastResponse) as JSONObject
                if (obj.containsKey("code") && obj.containsKey("msg")) {
                    throw APIException("ERROR: " + obj["code"] + ", " + obj["msg"], IllegalStateException())
                }
            }
        } catch (e: IOException) {
            throw APIException("Error in reading response!", e)
        }

        return this
    }

    fun sign(api: String, secret: String, options: Map<String, String>?): BinanceRequest {
        val humanMessage = "Please check the \'Exchanges\' tab, and ensure your API/Secret key are saved!"
        if (api.isEmpty())
            throw APIException("Missing Binance API key! " + humanMessage, IllegalArgumentException())
        if (secret.isEmpty())
            throw APIException("Missing Binance secret key! " + humanMessage, IllegalArgumentException())

        if (secret.isNotEmpty() && !request.contains("&signature=")) {
            val list = LinkedList<String>()
            if (options != null) {
                for (key in options.keys) {
                    list.add(key + "=" + options[key])
                }
            }
            list.add("recvWindow=" + 7000)
            list.add("timestamp=" + Date().time)
            val queryToAdd = list.joinToString("&")
            var query = ""
            BotLogger.debug("Signature: RequestUrl = %s".format(request))
            if (request.contains("?")) {
                query = request.substring(request.indexOf('?') + 1) + "&"
            }
            query += queryToAdd

            BotLogger.debug("Signature: queryToInclude=%s queryToAdd=%s".format(query, queryToAdd))
            try {
                val signature = encode(secret, query) // set the HMAC hash header
                val concatenator = if (request.contains("?")) "&" else "?"
                request += concatenator + queryToAdd + "&signature=" + signature
            } catch (e: Exception) {
                throw APIException("Encryption error!", e)
            }

        }
        properties["X-MBX-APIKEY"] = api
        properties["Content-Type"] = "application/x-www-form-urlencoded"
        return this
    }

    /**
     * Settings method as post, keeping interface fluid
     * @return this request object
     */
    fun post(): BinanceRequest {
        method = RequestMethod.POST
        return this
    }

    /**
     * Settings method as PUT, keeping interface fluid
     * @return this request object
     */
    fun put(): BinanceRequest {
        method = RequestMethod.PUT
        return this
    }

    /**
     * Settings method as DELETE, keeping interface fluid
     * @return this request object
     */
    fun delete(): BinanceRequest {
        method = RequestMethod.DELETE
        return this
    }

    fun encode(key: String, data: String): String {
        val hmac = Mac.getInstance("HmacSHA256")
        val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA256")
        hmac.init(keySpec)
        return Hex.encodeHexString(hmac.doFinal(data.toByteArray(Charsets.UTF_8)))
    }

    fun toJsonArray(): JSONArray {
        return parser.parse(lastResponse) as JSONArray
    }

    fun toJsonObject(): JSONObject {
        return parser.parse(lastResponse) as JSONObject
    }

}