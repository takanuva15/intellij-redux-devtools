package com.github.takanuva15.intellijreduxdevtools.toolwindow

import com.intellij.openapi.wm.RegisterToolWindowTask
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.kotest.matchers.collections.shouldHaveAtLeastSize

class RDToolWindowFactoryTest : BasePlatformTestCase() {
    val toolWindowId = "Redux DevTools"

    fun `test rd tool window activates`() {
        val windowManager = ToolWindowManager.getInstance(myFixture.project)
        val toolWindow = windowManager.registerToolWindow(RegisterToolWindowTask(id = toolWindowId))

        RDToolWindowFactory().createToolWindowContent(myFixture.project, toolWindow)

        ToolWindowManager
            .getInstance(myFixture.project)
            .getToolWindow(toolWindowId)!!
            .contentManager.contents
            .shouldHaveAtLeastSize(1)
    }
}
