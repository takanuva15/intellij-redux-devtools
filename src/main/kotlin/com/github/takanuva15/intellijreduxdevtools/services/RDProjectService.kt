package com.github.takanuva15.intellijreduxdevtools.services

import com.github.takanuva15.intellijreduxdevtools.RDBundle
import com.github.takanuva15.intellijreduxdevtools.ui.RDPanel
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class RDProjectService(
    project: Project,
) {
    val rdWindow = RDPanel(project)

    init {
        thisLogger().info(RDBundle.message("projectService", project.name))
    }
}
