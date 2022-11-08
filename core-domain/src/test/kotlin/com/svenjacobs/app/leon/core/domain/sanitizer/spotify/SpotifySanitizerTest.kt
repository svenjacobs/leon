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

package com.svenjacobs.app.leon.core.domain.sanitizer.spotify

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class SpotifySanitizerTest : WordSpec(
	{

		"invoke" should {

			"remove \"si\" parameter" {
				val sanitizer = SpotifySanitizer()

				val result = sanitizer(
					"https://open.spotify.com/album/5N2BIKomahKMAAirp8tiBN?si=BICcHVzTTqmqt" +
						"82Y6f2e_A&utm_source=native-share-menu",
				)

				result shouldBe "https://open.spotify.com/album/5N2BIKomahKMAAirp8tiBN&utm_source" +
					"=native-share-menu"
			}
		}
	},
)
