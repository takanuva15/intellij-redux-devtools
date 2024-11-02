// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.github.takanuva15.intellijreduxdevtools.ui

import com.intellij.diagnostic.ThreadDumper
import com.intellij.ui.jcef.JBCefBrowserBase
import com.intellij.util.ui.UIUtil
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import org.junit.Assert
import java.awt.BorderLayout
import java.awt.EventQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.function.BooleanSupplier
import javax.swing.JFrame

object JBCefTestHelper {
    /**
     * Shows the browser in a frame in waits for a load completion.
     */
    fun showAndWaitForLoad(
        browser: JBCefBrowserBase,
        frameTitle: String,
    ) {
        invokeAndWaitForLoad(browser) { show(browser, frameTitle) }
    }

    /**
     * Shows the browser in a frame asynchronously.
     */
    fun showAsync(
        browser: JBCefBrowserBase,
        frameTitle: String,
    ) {
        EventQueue.invokeLater { show(browser, frameTitle) }
    }

    private fun show(
        browser: JBCefBrowserBase,
        frameTitle: String,
    ) {
        val frame = JFrame(frameTitle)
        frame.setSize(640, 480)
        frame.setLocationRelativeTo(null)
        frame.add(browser.component, BorderLayout.CENTER)
        frame.isVisible = true
    }

    /**
     * Invokes and waits for a load completion. Either the runnable should load URL/HTML or the browser should be created with initial URL/HTML.
     */
    fun invokeAndWaitForLoad(
        browser: JBCefBrowserBase,
        runnable: Runnable,
    ) {
        val latch = CountDownLatch(1)

        browser.jbCefClient.addLoadHandler(
            object : CefLoadHandlerAdapter() {
                override fun onLoadEnd(
                    cefBrowser: CefBrowser,
                    frame: CefFrame,
                    httpStatusCode: Int,
                ) {
                    println("onLoadEnd on " + browser + " for " + browser.cefBrowser.url)
                    browser.jbCefClient.removeLoadHandler(this, cefBrowser)
                    latch.countDown()
                }
            },
            browser.cefBrowser,
        )

        invokeAndWaitForLatch(latch, runnable)
    }

    /**
     * Invokes and waits for the condition to become true.
     */
    fun invokeAndWaitForCondition(
        runnable: Runnable,
        condition: BooleanSupplier,
    ) {
        val latch = CountDownLatch(1)

        invokeAndWaitForLatch(latch) {
            runnable.run()
            latch.countDown()
        }

        try {
            while (!condition.asBoolean) {
                Thread.sleep(100)
            }
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    fun invokeAndWaitForLatch(
        latch: CountDownLatch,
        runnable: Runnable,
    ) {
        UIUtil.invokeLaterIfNeeded(runnable)
        await(latch)
    }

    fun await(latch: CountDownLatch) {
        try {
            if (!latch.await(5, TimeUnit.SECONDS)) {
                Assert.fail(
                    """
                    timeout:
                    ${ThreadDumper.dumpThreadsToString()}
                    """.trimIndent(),
                )
            }
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}
