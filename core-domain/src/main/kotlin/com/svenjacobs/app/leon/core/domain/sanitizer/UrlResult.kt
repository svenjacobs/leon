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

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

data class UrlResult(
	val schema: String,
	val domain: String,
	val path: String,
	val queryArguments: ImmutableMap<String, QueryArgument>,
) {
	data class QueryArgument(val name: String, val value: String, val remove: Boolean)

	override fun toString(): String {
		val arguments = queryArguments.values
			.filterNot { it.remove }
			.joinToString(
				separator = "&",
			) { "${it.name}=${it.value}" }

		return StringBuilder("$schema$domain$path").apply {
			if (arguments.isNotEmpty()) {
				append("?$arguments")
			}
		}.toString()
	}
}

fun UrlResult.withQueryArguments(
	predicate: (name: String) -> Boolean,
	remove: Boolean = true,
): UrlResult = copy(
	queryArguments = queryArguments.mapValues { entry ->
		if (predicate(entry.key)) {
			entry.value.copy(remove = remove)
		} else {
			entry.value
		}
	}.toImmutableMap(),
)

fun UrlResult.withQueryArguments(names: Iterable<String>, remove: Boolean = true): UrlResult =
	withQueryArguments(
		predicate = { names.contains(it) },
		remove = remove,
	)

fun UrlResult.withQueryArgument(name: String, remove: Boolean = true): UrlResult =
	withQueryArguments(
		predicate = { name == it },
		remove = remove,
	)

fun UrlResult.withAllQueryArgumentsRemoved(): UrlResult = copy(
	queryArguments = queryArguments.mapValues { entry ->
		entry.value.copy(remove = true)
	}.toImmutableMap(),
)
