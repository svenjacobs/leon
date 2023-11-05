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

package com.svenjacobs.app.leon.core.common.url

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class UrlTest :
	WordSpec(
		{
			"http without path" When {

				"Url" should {
					val url = Url("http://www.domain.com")

					"parse schema" {
						url.schema shouldBe "http://"
					}

					"parse domain" {
						url.domain shouldBe "www.domain.com"
					}

					"parse path" {
						url.path shouldBe ""
					}

					"parse query arguments" {
						url.queryArguments.size shouldBe 0
					}
				}
			}

			"https without path" When {

				"Url" should {
					val url = Url("https://www.domain.com")

					"parse schema" {
						url.schema shouldBe "https://"
					}

					"parse domain" {
						url.domain shouldBe "www.domain.com"
					}

					"parse path" {
						url.path shouldBe ""
					}

					"parse query arguments" {
						url.queryArguments.size shouldBe 0
					}
				}
			}

			"https with empty path" When {

				"Url" should {
					val url = Url("https://www.domain.com/")

					"parse schema" {
						url.schema shouldBe "https://"
					}

					"parse domain" {
						url.domain shouldBe "www.domain.com"
					}

					"parse path" {
						url.path shouldBe "/"
					}

					"parse query arguments" {
						url.queryArguments.size shouldBe 0
					}
				}
			}

			"https with path" When {

				"Url" should {
					val url = Url("https://www.domain.com/some/path/")

					"parse schema" {
						url.schema shouldBe "https://"
					}

					"parse domain" {
						url.domain shouldBe "www.domain.com"
					}

					"parse path" {
						url.path shouldBe "/some/path/"
					}

					"parse query arguments" {
						url.queryArguments.size shouldBe 0
					}
				}
			}

			"https with path and query arguments" When {

				"Url" should {
					val url = Url(
						"https://www.domain.com/some/path/?query1=value1&query2=&query3=value3",
					)

					"parse schema" {
						url.schema shouldBe "https://"
					}

					"parse domain" {
						url.domain shouldBe "www.domain.com"
					}

					"parse path" {
						url.path shouldBe "/some/path/"
					}

					"parse query arguments" {
						url.queryArguments.size shouldBe 3
						url.queryArguments["query1"] shouldBe "value1"
						url.queryArguments["query2"] shouldBe ""
						url.queryArguments["query3"] shouldBe "value3"
					}
				}
			}

			"invalid url" When {

				"Url" should {

					"throw exception" {
						shouldThrow<IllegalArgumentException> {
							Url("www.domain.com")
						}
					}
				}
			}
		},
	)
