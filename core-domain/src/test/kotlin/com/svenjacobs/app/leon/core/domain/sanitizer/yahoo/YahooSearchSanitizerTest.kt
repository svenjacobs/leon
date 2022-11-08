/*
 * Léon - The URL Cleaner
 * Copyright (C) 2022 Sven Jacobs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.svenjacobs.app.leon.core.domain.sanitizer.yahoo

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class YahooSearchSanitizerTest : WordSpec(
	{

		"invoke" should {

			"extract URL from Yahoo search link" {
				val sanitizer = YahooSearchSanitizer()

				val result = sanitizer(
					"https://r.search.yahoo.com/_ylt=A0geKLovoVtisIEAUapx.9w4;_ylu=Y29sbwNi" +
						"ZjEEcG9zAzQEdnRpZAMEc2VjA3Ny/RV=2/RE=1650201007/RO=10/RU=https%3a%2f%2fg" +
						"ithub.com%2fsvenjacobs%2fleon/RK=2/RS=rHoItccMzwyZAXsJuDMkBaKUMx0-",
				)

				result shouldBe "https://github.com/svenjacobs/leon"
			}
		}
	},
)
