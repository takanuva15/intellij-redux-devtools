package com.github.takanuva15.intellijreduxdevtools.actions

import com.github.takanuva15.intellijreduxdevtools.RDBundle
import com.github.takanuva15.intellijreduxdevtools.services.RDProjectService
import com.github.takanuva15.intellijreduxdevtools.toolwindow.RD_TOOL_WINDOW_ID
import com.github.takanuva15.intellijreduxdevtools.ui.RD_HOMEPAGE
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.wm.ToolWindowBalloonShowOptions
import com.intellij.openapi.wm.ToolWindowManager

class ReloadRDPanelAction :
    DumbAwareAction(
        RDBundle.msg("actions.reloadrdpanel.tooltip"),
        RDBundle.msg("actions.reloadrdpanel.description"),
        AllIcons.Actions.Refresh,
    ) {
    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        e.project!!
            .service<RDProjectService>()
            .rdWindow.cefBrowser
            .loadURL(RD_HOMEPAGE)
        ToolWindowManager.getInstance(e.project!!).notifyByBalloon(
            ToolWindowBalloonShowOptions(
                RD_TOOL_WINDOW_ID,
                MessageType.INFO,
                RDBundle.msg("actions.reloadrdpanel.confirmation"),
                AllIcons.General.InspectionsOK,
                balloonCustomizer = {
                    it.setFadeoutTime(2000)
                },
            ),
        )
    }
}
