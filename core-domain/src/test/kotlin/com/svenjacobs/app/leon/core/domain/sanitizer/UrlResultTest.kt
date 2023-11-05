/*
 * Léon - The URL Cleaner
 * Copyright (C) 2023 Sven Jacobs
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

package com.svenjacobs.app.leon.core.domain.sanitizer

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentMapOf

class UrlResultTest :
	WordSpec(
		{
			val urlResult = UrlResult(
				schema = "https://",
				domain = "www.test.example",
				path = "/test",
				queryArguments = persistentMapOf(
					"arg1" to UrlResult.QueryArgument(
						name = "arg1",
						value = "value1",
						remove = false,
					),
					"arg2" to UrlResult.QueryArgument(
						name = "arg2",
						value = "value2",
						remove = false,
					),
					"arg3" to UrlResult.QueryArgument(
						name = "arg3",
						value = "value3",
						remove = false,
					),
				),
			)

			"withQueryArguments (predicate)" should {

				"remove all matching query arguments" {
					val result = urlResult.withQueryArguments({ it == "arg2" || it == "arg3" })
					result.queryArguments.values.map { it.remove } shouldBe listOf(
						false,
						true,
						true,
					)
				}
			}

			"withQueryArguments (names)" should {

				"remove all matching query arguments" {
					val result = urlResult.withQueryArguments(listOf("arg1", "arg3"))
					result.queryArguments.values.map { it.remove } shouldBe listOf(
						true,
						false,
						true,
					)
				}
			}

			"withQueryArgument" should {

				"remove query argument" {
					val result = urlResult.withQueryArgument("arg3", true)
					result.queryArguments.getValue("arg3").remove shouldBe true
				}
			}

			"withAllQueryArgumentsRemoved" should {

				"set remove to true for all query arguments" {
					val result = urlResult.withAllQueryArgumentsRemoved()
					result.queryArguments.all { entry -> entry.value.remove } shouldBe true
				}
			}

			"toString" should {

				"return URL" {
					urlResult.toString() shouldBe
						"https://www.test.example/test?arg1=value1&arg2=value2&arg3=value3"
				}

				"return URL without removed arguments (via predicate)" {
					urlResult.withQueryArguments({ it == "arg1" || it == "arg3" })
						.toString() shouldBe "https://www.test.example/test?arg2=value2"
				}

				"return URL without removed arguments (all)" {
					urlResult.withAllQueryArgumentsRemoved()
						.toString() shouldBe "https://www.test.example/test"
				}

				"return URL without path" {
					urlResult.copy(
						path = "",
					)
						.toString() shouldBe "https://www.test.example?arg1=value1&arg2=value2&arg3=value3"
				}

				"return URL without query arguments" {
					urlResult.copy(
						queryArguments = persistentMapOf(),
					).toString() shouldBe "https://www.test.example/test"
				}
			}
		},
	)
