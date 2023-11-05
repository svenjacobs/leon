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

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

/**
 * Breaks down given string, which should be a URL, into its parts [schema], [domain], [path] and
 * [queryArguments].
 *
 * @throws IllegalArgumentException If string cannot be parsed as a URL
 */
class Url constructor(url: String) {

	val schema: String
	val domain: String
	val path: String
	val queryArguments: ImmutableMap<String, String>

	init {
		val result = REGEX_SCHEMA_DOMAIN_PATH.find(url) ?: throw IllegalArgumentException()

		schema = result.groupValues[1]
		domain = result.groupValues[2]
		path = result.groupValues[3]

		queryArguments = REGEX_QUERY_ARGUMENTS.findAll(url)
			.map { it.groupValues[1] to it.groupValues[2] }
			.toMap()
			.toImmutableMap()
	}

	private companion object {
		/**
		 * 1st group = schema
		 * 2nd group = domain
		 * 3rd group = path
		 */
		private val REGEX_SCHEMA_DOMAIN_PATH = Regex("^(https?://)(.*?)(?:\$|(/[^?&]*))")

		/**
		 * 1st group = name
		 * 2nd group = value
		 */
		private val REGEX_QUERY_ARGUMENTS = Regex("[?&]([^=]+)=([^&]*)")
	}
}
