package com.svenjacobs.app.leon.domain

import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer

object Defaults {

    val SANITIZERS = listOf(
        QueryParameterSanitizer(
            parameterName = "wt_mc",
            name = "wt_mc",
            description = "Webtrekk",
            isDefault = true,
        ),
        QueryParameterSanitizer(
            parameterName = "utm_source",
            name = "utm_source",
            description = "Urchin Tracking Module (Google Analytics)",
            isDefault = true,
        ),
        QueryParameterSanitizer(
            parameterName = "utm_medium",
            name = "utm_medium",
            description = "Urchin Tracking Module (Google Analytics)",
            isDefault = true,
        ),
        QueryParameterSanitizer(
            parameterName = "utm_campaign",
            name = "utm_campaign",
            description = "Urchin Tracking Module (Google Analytics)",
            isDefault = true,
        ),
        QueryParameterSanitizer(
            parameterName = "utm_term",
            name = "utm_term",
            description = "Urchin Tracking Module (Google Analytics)",
            isDefault = true,
        ),
        QueryParameterSanitizer(
            parameterName = "utm_content",
            name = "utm_content",
            description = "Urchin Tracking Module (Google Analytics)",
            isDefault = true,
        ),
    )
}
