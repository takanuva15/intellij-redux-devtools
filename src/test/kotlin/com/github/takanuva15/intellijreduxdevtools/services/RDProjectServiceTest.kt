package com.github.takanuva15.intellijreduxdevtools.services

import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.kotest.matchers.nulls.shouldNotBeNull

class RDProjectServiceTest : BasePlatformTestCase() {
    fun `test service has html window`() {
        val window = myFixture.project.service<RDProjectService>().rdWindow
        window.shouldNotBeNull()
    }
}
