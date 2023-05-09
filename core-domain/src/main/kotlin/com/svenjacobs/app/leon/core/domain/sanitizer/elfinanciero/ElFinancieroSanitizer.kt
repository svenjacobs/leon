package com.svenjacobs.app.leon.core.domain.sanitizer.elfinanciero

import android.content.Context
import com.svenjacobs.app.leon.core.common.regex.RegexFactory
import com.svenjacobs.app.leon.core.domain.R
import com.svenjacobs.app.leon.core.domain.sanitizer.RegexSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.Sanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerId

class ElFinancieroSanitizer : RegexSanitizer(
	regex = RegexFactory.ofParameter("outputType"),
) {

	override val id = SanitizerId("elfinanciero")

	override fun getMetadata(context: Context) = Sanitizer.Metadata(
		name = context.getString(R.string.sanitizer_elfinanciero_name),
	)
}
