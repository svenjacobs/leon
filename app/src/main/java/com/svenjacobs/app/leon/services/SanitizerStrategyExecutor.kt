package com.svenjacobs.app.leon.services

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer
import javax.inject.Inject

class SanitizerStrategyExecutor @Inject constructor(
    private val queryParameterSanitizerStrategy: QueryParameterSanitizerStrategy,
) {

    fun sanitize(
        sanitizer: Sanitizer,
        input: String,
    ): SanitizerStrategy.Result =
        when (sanitizer) {
            is QueryParameterSanitizer -> queryParameterSanitizerStrategy.sanitize(
                sanitizer,
                input
            )
        }
}
