package com.github.takanuva15.intellijreduxdevtools.ui

import com.intellij.openapi.util.registry.Registry
import com.intellij.ui.jcef.JCEFHtmlPanel
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefSchemeHandlerFactory
import org.cef.handler.CefResourceHandler
import org.cef.network.CefRequest

class RDPanel : JCEFHtmlPanel(isOffScreenRendering(), null, null) {
    init {
        CefApp.getInstance().registerSchemeHandlerFactory("http", "redux-devtools", LocalFileSchemeHandlerFactory())
        loadURL("http://redux-devtools/index.html")
    }

    companion object {
        private fun isOffScreenRendering(): Boolean = Registry.`is`("ide.browser.jcef.redux-devtools.osr.enabled")
    }
}

class LocalFileSchemeHandlerFactory : CefSchemeHandlerFactory {
    override fun create(
        cefBrowser: CefBrowser,
        cefFrame: CefFrame,
        s: String,
        cefRequest: CefRequest,
    ): CefResourceHandler = LocalFileResourceHandler("http://redux-devtools", "redux-devtools/")
}
