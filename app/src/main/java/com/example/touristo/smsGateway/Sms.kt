package com.example.touristo.smsGateway
import java.io.IOException
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class Sms(private val mobile: String, private val msg: String) {
    companion object {
        @Throws(IOException::class)
        fun send(mobile: String, message: String) {
            val url = URL("https://sms.send.lk/api/v3/sms/send")
            val http = url.openConnection() as HttpURLConnection
            http.requestMethod = "POST"
            http.doOutput = true
            http.setRequestProperty("Authorization", "Bearer 1102|63zKUEcqXIOjKCbQFmI3PJajH0v1Q0mIkUHg7fNZ")
            http.setRequestProperty("Content-Type", "application/json")

            val data = "{\"recipient\": \"$mobile\" , \"sender_id\" : \"SendTest\" , \"message\":\"$message\"}"
            val out = data.toByteArray(StandardCharsets.UTF_8)

            val stream: OutputStream = http.outputStream
            stream.write(out)

            println("${http.responseCode} ${http.responseMessage}")
            http.disconnect()
        }
    }

    @Throws(IOException::class)
    fun send() {
        send(mobile, msg)
    }
}
