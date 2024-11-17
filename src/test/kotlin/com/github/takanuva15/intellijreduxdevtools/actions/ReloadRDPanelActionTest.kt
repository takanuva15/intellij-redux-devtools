package com.github.takanuva15.intellijreduxdevtools.actions

import com.github.takanuva15.intellijreduxdevtools.services.RDProjectService
import com.github.takanuva15.intellijreduxdevtools.ui.RD_HOMEPAGE
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.replaceService
import com.intellij.toolWindow.ToolWindowHeadlessManagerImpl
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify

class ReloadRDPanelActionTest : BasePlatformTestCase() {
    fun `test action runs in the background`() {
        ReloadRDPanelAction().actionUpdateThread shouldBe ActionUpdateThread.BGT
    }

    fun `test action reloads browser`() {
        val mockRdSvc = mockk<RDProjectService>()
        every { mockRdSvc.rdWindow.cefBrowser.loadURL(any()) } just Runs
        project.replaceService(RDProjectService::class.java, mockRdSvc, testRootDisposable)
        val spyToolWindowManager = spyk(ToolWindowHeadlessManagerImpl(project))
        project.replaceService(ToolWindowManager::class.java, spyToolWindowManager, testRootDisposable)

        myFixture.testAction(ReloadRDPanelAction())
        verify { mockRdSvc.rdWindow.cefBrowser.loadURL(RD_HOMEPAGE) }
        verify { spyToolWindowManager.notifyByBalloon(any()) }
    }
}
