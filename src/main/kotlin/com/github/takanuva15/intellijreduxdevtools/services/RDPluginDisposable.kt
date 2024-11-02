package com.github.takanuva15.intellijreduxdevtools.services

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service

@Service(Service.Level.PROJECT)
class RDPluginDisposable : Disposable {
    override fun dispose() {
    }
}
