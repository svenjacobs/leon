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

package com.svenjacobs.app.leon.feature.sanitizer.google.search

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class GoogleSearchSanitizerTest : WordSpec({

    "invoke" should {

        "extract URL from Google search link" {
            val sanitizer = GoogleSearchSanitizer()

            val result = sanitizer(
                "https://www.google.com/url?sa=t&source=web&rct=j&url=https://www.regextester.com/&ved=2ahUKEwiTpvflqP34AhXOgv0HHSNQCOIQFnoECAcQAQ&usg=AOvVaw1wBmEA7TD90QkZPu7zcsOa"
            )

            result shouldBe "https://www.regextester.com/"
        }
    }
})
