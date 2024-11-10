package com.github.takanuva15.intellijreduxdevtools.ui

import com.intellij.testFramework.TestApplicationManager
import com.intellij.ui.jcef.JBCefClient
import com.intellij.ui.jcef.executeJavaScript
import com.intellij.util.SystemProperties
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Derived from JBCefHeadlessOsrTest in intellij-community
 */
class RDPanelTest {
    init {
        SystemProperties.setProperty("java.awt.headless", "false")
        TestApplicationManager.getInstance()
    }

    @Test
    fun `test rd tool window activates`() {
        val panel = RDPanel()
        panel.jbCefClient.setProperty(JBCefClient.Properties.JS_QUERY_POOL_SIZE, 1)
        JBCefTestHelper.invokeAndWaitForLoad(panel) {
            panel.createImmediately()
        }
        runBlocking {
            val res = panel.executeJavaScript("""document.getElementById("root").childElementCount""")
            res.shouldBe("1")
        }

        // Uncomment to view if devtools loads properly in sample frame
//        JBCefTestHelper.showAndWaitForLoad(panel, "Test Frame")
//        Thread.sleep(2000)
    }
}
