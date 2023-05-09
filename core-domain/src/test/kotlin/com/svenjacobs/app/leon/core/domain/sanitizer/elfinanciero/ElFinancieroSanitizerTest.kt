package com.svenjacobs.app.leon.core.domain.sanitizer.elfinanciero

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class ElFinancieroSanitizerTest : WordSpec(
	{
		"invoke" should {
			"remove \"outputType\" parameter" {
				val sanitizer = ElFinancieroSanitizer()

				val result = sanitizer(
					"https://www.elfinanciero.com.mx/food-and-drink/2023/01/04/" +
						"dia-de-reyes-2023-donde-comprar-rosca-de-tacos-en-la-cdmx/?outputType=amp",
				)

				result shouldBe "https://www.elfinanciero.com.mx/food-and-drink/2023/01/04/" +
					"dia-de-reyes-2023-donde-comprar-rosca-de-tacos-en-la-cdmx/"
			}
		}
	},
)
