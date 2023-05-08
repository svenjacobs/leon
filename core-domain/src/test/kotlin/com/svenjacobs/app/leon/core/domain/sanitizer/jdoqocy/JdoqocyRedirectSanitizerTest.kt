package com.svenjacobs.app.leon.core.domain.sanitizer.jdoqocy

import io.kotest.matchers.shouldBe
import io.kotest.core.spec.style.WordSpec

class JdoqocyRedirectSanitizerTest : WordSpec(
	{

		"invoke" should {

			"extract URL from Jdoqocy redirect link" {
				val sanitizer = JdoqocyRedirectSanitizer()
				val result = sanitizer(
					"https://www.jdoqocy.com/click-7988170-15232592?SID=11003b6m4t07&url=https%3A%2F%2Fwww.gog.com%2Fde%2Fgame%2Falwas_awakening",
				)

				result shouldBe "https://www.gog.com/de/game/alwas_awakening"
			}
		}
	},
)
