package com.svenjacobs.app.leon.repository.db.mapper

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer.Type
import javax.inject.Inject

class DbSanitizerMapper @Inject constructor() {

    fun mapToDb(sanitizer: Sanitizer): DbSanitizer =
        when (sanitizer) {
            is QueryParameterSanitizer ->
                DbSanitizer(
                    uid = sanitizer.uid,
                    type = Type.QUERY_PARAMETER,
                    name = sanitizer.name,
                    data = mapOf(KEY_PARAMETER_NAME to sanitizer.parameterName),
                    description = sanitizer.description,
                    isDefault = sanitizer.isDefault,
                    isEnabled = sanitizer.isEnabled,
                )
        }

    fun mapFromDb(dbSanitizer: DbSanitizer): Sanitizer =
        when (dbSanitizer.type) {
            Type.QUERY_PARAMETER -> QueryParameterSanitizer(
                parameterName = dbSanitizer.data.getValue(KEY_PARAMETER_NAME),
                uid = dbSanitizer.uid,
                name = dbSanitizer.name,
                description = dbSanitizer.description,
                isDefault = dbSanitizer.isDefault,
                isEnabled = dbSanitizer.isEnabled,
            )
        }

    private companion object {
        private const val KEY_PARAMETER_NAME = "parameterName"
    }
}
