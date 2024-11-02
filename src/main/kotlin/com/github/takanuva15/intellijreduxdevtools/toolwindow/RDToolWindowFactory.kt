package com.github.takanuva15.intellijreduxdevtools.toolwindow

import com.github.takanuva15.intellijreduxdevtools.services.RDProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class RDToolWindowFactory :
    ToolWindowFactory,
    DumbAware {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        val rdToolWindow = RDToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(rdToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class RDToolWindow(
        toolWindow: ToolWindow,
    ) {
        private val service = toolWindow.project.service<RDProjectService>()

        fun getContent() =
            SimpleToolWindowPanel(true, true).apply {
                setContent(service.rdWindow.component)
            }
    }
}
