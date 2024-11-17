package com.github.takanuva15.intellijreduxdevtools.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.ui.Messages
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify

class ShowHelpActionTest : BasePlatformTestCase() {
    fun `test action runs in the background`() {
        ShowHelpAction().actionUpdateThread shouldBe ActionUpdateThread.BGT
    }

    fun `test action shows message`() {
        mockkStatic(Messages::class)
        every { Messages.showInfoMessage(any(), any()) } just Runs

        myFixture.testAction(ShowHelpAction())
        verify { Messages.showInfoMessage(any(), any()) }
    }
}
