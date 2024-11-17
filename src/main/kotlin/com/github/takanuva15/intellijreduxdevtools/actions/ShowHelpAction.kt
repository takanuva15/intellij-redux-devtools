package com.github.takanuva15.intellijreduxdevtools.actions

import com.github.takanuva15.intellijreduxdevtools.RDBundle
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages

class ShowHelpAction :
    DumbAwareAction(
        RDBundle.msg("actions.showhelp.tooltip"),
        RDBundle.msg("actions.showhelp.description"),
        AllIcons.Actions.Help,
    ) {
    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        Messages.showInfoMessage(RDBundle.msg("rdtoolwindow.help.message"), RDBundle.msg("rdtoolwindow.help.title"))
    }
}
