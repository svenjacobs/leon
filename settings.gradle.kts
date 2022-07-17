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

rootProject.name = "Leon"
include(
    ":core-common",
    ":core-domain",
    ":feature-sanitizer-amazon",
    ":feature-sanitizer-amazon-smile",
    ":feature-sanitizer-empty-parameters",
    ":feature-sanitizer-facebook",
    ":feature-sanitizer-flipkart",
    ":feature-sanitizer-google-analytics",
    ":feature-sanitizer-google-search",
    ":feature-sanitizer-instagram",
    ":feature-sanitizer-netflix",
    ":feature-sanitizer-session-ids",
    ":feature-sanitizer-spotify",
    ":feature-sanitizer-twitter",
    ":feature-sanitizer-webtrekk",
    ":feature-sanitizer-yahoo-search",
    ":app",
)
