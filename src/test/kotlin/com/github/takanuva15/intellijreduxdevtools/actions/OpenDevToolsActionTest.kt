package com.github.takanuva15.intellijreduxdevtools.actions

import com.github.takanuva15.intellijreduxdevtools.services.RDProjectService
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.registry.Registry
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.replaceService
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify

class OpenDevToolsActionTest : BasePlatformTestCase() {
    fun `test action runs in the background`() {
        OpenDevToolsAction().actionUpdateThread shouldBe ActionUpdateThread.BGT
    }

    fun `test action opens browser devtools`() {
        val mockRdSvc = mockk<RDProjectService>()
        every { mockRdSvc.rdWindow.openDevtools() } just Runs
        project.replaceService(RDProjectService::class.java, mockRdSvc, testRootDisposable)

        myFixture.testAction(OpenDevToolsAction())
        verify { mockRdSvc.rdWindow.openDevtools() }
    }

    fun `test action does not show if the IDE is not in internal mode and registry key is not set`() {
        val app = spyk(ApplicationManager.getApplication())
        ApplicationManager.setApplication(app, testRootDisposable)
        every { app.isInternal } returns false

        val resultPresentation = myFixture.testAction(OpenDevToolsAction())
        resultPresentation.isEnabledAndVisible.shouldBeFalse()
    }

    fun `test action shows if the IDE is not in internal mode but registry key is set`() {
        val app = spyk(ApplicationManager.getApplication())
        ApplicationManager.setApplication(app, testRootDisposable)
        every { app.isInternal } returns false
        mockkStatic(Registry::class)
        every { Registry.`is`(REG_KEY_SHOW_DEVTOOLS) } returns true

        val resultPresentation = myFixture.testAction(OpenDevToolsAction())
        resultPresentation.isEnabledAndVisible.shouldBeTrue()
    }

    fun `test action shows if the IDE is in internal mode`() {
        val app = spyk(ApplicationManager.getApplication())
        ApplicationManager.setApplication(app, testRootDisposable)
        every { app.isInternal } returns true

        val resultPresentation = myFixture.testAction(OpenDevToolsAction())
        resultPresentation.isEnabledAndVisible.shouldBeTrue()
    }
}
