package com.svenjacobs.app.leon.core.domain.sanitizer.jdoqocy

import android.content.Context
import com.svenjacobs.app.leon.core.common.regex.RegexFactory
import com.svenjacobs.app.leon.core.domain.sanitizer.Sanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerId
import com.svenjacobs.app.leon.core.domain.R
import com.svenjacobs.app.leon.core.domain.sanitizer.SearchResultSanitizer

class JdoqocyRedirectSanitizer : SearchResultSanitizer(
	RegexFactory.ofParameter("url"),
) {

	override val id = SanitizerId("jdoqocy")

	override fun getMetadata(context: Context) = Sanitizer.Metadata(
		name = context.getString(R.string.sanitizer_jdoqocy_redirect_name),
	)

	override fun matchesDomain(input: String) = DOMAIN_REGEX.containsMatchIn(input)

	private companion object {
		private val DOMAIN_REGEX = Regex("jdoqocy\\.com/click")
	}
}
