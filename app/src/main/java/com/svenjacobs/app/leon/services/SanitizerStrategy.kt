package com.svenjacobs.app.leon.services

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer
import com.svenjacobs.app.leon.services.SanitizerStrategy.Result
import javax.inject.Inject

interface SanitizerStrategy<T : Sanitizer> {

    data class Result(
        val output: String,
        val artifactsRemoved: Int,
    )

    fun sanitize(
        sanitizer: T,
        input: String
    ): Result
}

class QueryParameterSanitizerStrategy @Inject constructor() :
    SanitizerStrategy<QueryParameterSanitizer> {

    override fun sanitize(
        sanitizer: QueryParameterSanitizer,
        input: String,
    ): Result {
        val regex = Regex("[?&]${sanitizer.name}=.[^&]*")
        return when (regex.containsMatchIn(input)) {
            true -> Result(
                output = regex.replace(input, ""),
                artifactsRemoved = 1
            )
            false -> Result(
                output = input,
                artifactsRemoved = 0,
            )
        }
    }
}
