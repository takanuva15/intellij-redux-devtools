package com.github.takanuva15.intellijreduxdevtools

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

@NonNls
private const val BUNDLE = "messages.RDBundle"

object RDBundle : DynamicBundle(BUNDLE) {
    @JvmStatic
    fun msg(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any,
    ) = getMessage(key, *params)

    @Suppress("unused")
    @JvmStatic
    fun msgPtr(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any,
    ) = getLazyMessage(key, *params)
}
