package com.github.takanuva15.intellijreduxdevtools.services

import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class RDPluginDisposableTest : BasePlatformTestCase() {
    fun `test service is disposable`() {
        val svc = myFixture.project.service<RDPluginDisposable>()
        svc.dispose()
    }
}
