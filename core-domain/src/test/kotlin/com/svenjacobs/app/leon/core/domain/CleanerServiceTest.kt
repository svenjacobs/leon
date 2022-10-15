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

package com.svenjacobs.app.leon.core.domain

import com.svenjacobs.app.leon.core.common.regex.RegexFactory
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerId
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList

class CleanerServiceTest : WordSpec(
	{

		val repository = mockk<SanitizerRepository>()

		val service = CleanerService(
			sanitizers = listOf(
				MockSanitizer(
					id = SanitizerId("mock1"),
					name = "mock1",
					regex = RegexFactory.ofParameter("paramA"),
				),
				MockSanitizer(
					id = SanitizerId("mock2"),
					name = "mock2",
					regex = RegexFactory.ofParameter("paramB"),
				),
				MockSanitizer(
					id = SanitizerId("mock3"),
					name = "mock3",
					regex = RegexFactory.ofParameter("paramC"),
				),
			).toImmutableList(),
			repository = repository,
		)

		beforeEach {
			clearMocks(
				repository,
			)

			coEvery { repository.isEnabled(SanitizerId(any())) } returns true
		}

		"clean" should {

			"remove tracking parameters" {
				val result = service.clean(
					"https://www.some.site/?paramA=A&paramB=B",
				)

				result.cleanedText shouldBe "https://www.some.site/"
				result.urls shouldHaveSize 1
				result.urls.first() shouldBe "https://www.some.site/"
			}

			"keep non-tracking parameters" {
				val result = service.clean(
					"https://www.some.site/?paramA=A&paramB=B&page=1&q=query",
				)

				result.cleanedText shouldBe "https://www.some.site/?page=1&q=query"
				result.urls shouldHaveSize 1
				result.urls.first() shouldBe "https://www.some.site/?page=1&q=query"
			}

			"keep anchor tags after cleaned parameters" {
				val result = service.clean(
					"https://www.some.site/?paramA=A&paramB=B#anchor",
				)

				result.cleanedText shouldBe "https://www.some.site/#anchor"
				result.urls.first() shouldBe "https://www.some.site/#anchor"
			}

			"clean multiple URLs in text" {
				val result = service.clean(
					"Hey, I would like to share this " +
						"https://www.some.site/?paramA=A&paramB=B link as well as this " +
						"https://www.some2.site?paramC=C link :)",
				)

				result.cleanedText shouldBe "Hey, I would like to share this " +
					"https://www.some.site/ link as well as this https://www.some2.site link :)"
				result.urls shouldHaveSize 2
				result.urls[0] shouldBe "https://www.some.site/"
				result.urls[1] shouldBe "https://www.some2.site"
			}

			"URL decode when enabled" {
				val result = service.clean(
					text = "https://www.some.site/Hello%2FWorld?paramA=A&paramB=B",
					decodeUrl = true,
				)

				result.cleanedText shouldBe "https://www.some.site/Hello/World"
			}
		}
	},
)
