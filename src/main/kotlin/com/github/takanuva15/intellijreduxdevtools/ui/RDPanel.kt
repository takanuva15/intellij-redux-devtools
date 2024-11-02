package com.github.takanuva15.intellijreduxdevtools.ui

import com.github.takanuva15.intellijreduxdevtools.services.RDPluginDisposable
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.registry.Registry
import com.intellij.ui.jcef.JCEFHtmlPanel
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefSchemeHandlerFactory
import org.cef.handler.CefResourceHandler
import org.cef.network.CefRequest

class RDPanel(
    project: Project,
) : JCEFHtmlPanel(isOffScreenRendering(), null, null) {
    init {
        CefApp.getInstance().registerSchemeHandlerFactory("http", "redux-devtools", LocalFileSchemeHandlerFactory())
        loadURL("http://redux-devtools/index.html")
        Disposer.register(project.service<RDPluginDisposable>(), this)
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
