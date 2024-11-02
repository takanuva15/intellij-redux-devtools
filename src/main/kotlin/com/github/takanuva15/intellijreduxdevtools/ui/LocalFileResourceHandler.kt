package com.github.takanuva15.intellijreduxdevtools.ui

import org.cef.callback.CefCallback
import org.cef.handler.CefResourceHandlerAdapter
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefRequest
import org.cef.network.CefResponse
import java.io.InputStream
import java.net.URLConnection

/**
 * Converts web requests within the tool window into local file reads from the /resources folder
 *
 * Derived from:
 *  - https://medium.com/virtuslab/creating-intellij-plugin-with-webview-3b27c3f87aea
 *  - https://github.com/xm-online/xm-online-idea-plugin/blob/master/src/main/kotlin/com/icthh/xm/actions/WebDialog.kt#L245
 */
class LocalFileResourceHandler(
    val hostToConvert: String,
    val filePathToConvertTo: String,
) : CefResourceHandlerAdapter() {
    private lateinit var fileConnection: URLConnection
    private lateinit var fileInputStream: InputStream

    override fun processRequest(
        request: CefRequest,
        callback: CefCallback,
    ): Boolean {
        val requestedResourceFilePath = request.url.replace(hostToConvert, filePathToConvertTo)
        val requestedResource = javaClass.getClassLoader().getResource(requestedResourceFilePath)
        fileConnection = requestedResource!!.openConnection()
        fileInputStream = fileConnection.getInputStream()
        callback.Continue()
        return true
    }

    override fun getResponseHeaders(
        response: CefResponse,
        responseLength: IntRef,
        redirectUrl: StringRef,
    ) {
        response.mimeType =
            with(fileConnection.url.toString()) {
                when {
                    contains("html") -> "text/html"
                    contains("js") -> "text/javascript"
                    contains("css") -> "text/css"
                    else -> fileConnection.contentType
                }
            }
        response.status = 200
    }

    override fun readResponse(
        dataOut: ByteArray,
        bytesToRead: Int,
        bytesRead: IntRef,
        callback: CefCallback?,
    ): Boolean {
        val availableSize = fileInputStream.available()
        if (availableSize > 0) {
            var bytesToRead = Math.min(bytesToRead, availableSize)
            bytesToRead = fileInputStream.read(dataOut, 0, bytesToRead)
            bytesRead.set(bytesToRead)
            return true
        }

        bytesRead.set(0)
        fileInputStream.close()
        return false
    }

    override fun cancel() {
        fileInputStream.close()
    }
}
