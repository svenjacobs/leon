package com.svenjacobs.app.leon.domain.model

sealed class Sanitizer(
    open val uid: Int,
    open val name: String,
    open val description: String?,
    open val isDefault: Boolean,
    open val isEnabled: Boolean,
) {

    abstract fun withEnabled(isEnabled: Boolean): Sanitizer

    // TODO: Implement a regex-based query parameter sanitizer
    data class QueryParameterSanitizer(
        val parameterName: String,
        override val uid: Int = 0,
        override val name: String,
        override val description: String? = null,
        override val isDefault: Boolean = false,
        override val isEnabled: Boolean = true,
    ) : Sanitizer(uid, name, description, isDefault, isEnabled) {

        override fun withEnabled(isEnabled: Boolean) =
            copy(isEnabled = isEnabled)
    }
}
