package com.leetbot.api.impl.binance.http

import com.leetbot.api.http.Request
import com.leetbot.api.throwable.APIException
import com.leetbot.commons.utils.BotLogger
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


/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @since 2/19/2018
 * @version 1.0.0
 */
class BinanceRequest(private val request: String): Request {

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
    val properties: Map<String, String> = HashMap()
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

    override fun encode() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toJsonArray(): JSONArray {
        return parser.parse(lastResponse) as JSONArray
    }

    fun toJsonObject(): JSONObject {
        return parser.parse(lastResponse) as JSONObject
    }

}