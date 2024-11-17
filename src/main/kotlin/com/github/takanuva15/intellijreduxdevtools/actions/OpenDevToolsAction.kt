package com.github.takanuva15.intellijreduxdevtools.actions

import com.github.takanuva15.intellijreduxdevtools.RDBundle
import com.github.takanuva15.intellijreduxdevtools.services.RDProjectService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.util.registry.Registry

const val REG_KEY_SHOW_DEVTOOLS = "com.github.takanuva15.intellijreduxdevtools.showjcefdevtools"

class OpenDevToolsAction :
    DumbAwareAction(
        RDBundle.msg("actions.opendevtools.tooltip"),
        RDBundle.msg("actions.opendevtools.description"),
        AllIcons.Toolwindows.ToolWindowDebugger,
    ) {
    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        e.project!!
            .service<RDProjectService>()
            .rdWindow
            .openDevtools()
    }

    /**
     * Show the action only if the application is in [internal mode](https://plugins.jetbrains
     * .com/docs/intellij/enabling-internal.html) or the custom registry key is set.
     */
    override fun update(e: AnActionEvent) {
        e.presentation.isVisible = ApplicationManager.getApplication().isInternal ||
            Registry.`is`(REG_KEY_SHOW_DEVTOOLS)
    }
}
