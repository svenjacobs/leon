/*
 * Léon - The URL Cleaner
 * Copyright (C) 2021 Sven Jacobs
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

package com.svenjacobs.app.leon.service

import com.svenjacobs.app.lean.repository.CleanerRepositoryFake
import com.svenjacobs.app.leon.services.CleanerService
import com.svenjacobs.app.leon.services.QueryParameterSanitizerStrategy
import com.svenjacobs.app.leon.services.RegexSanitizerStrategy
import com.svenjacobs.app.leon.services.SanitizerStrategyExecutor
import com.svenjacobs.app.leon.services.model.CleaningResult
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.be
import io.kotest.matchers.collections.haveSize
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf

class CleanerServiceTest : ShouldSpec({

    val executor = SanitizerStrategyExecutor(
        QueryParameterSanitizerStrategy(),
        RegexSanitizerStrategy(),
    )

    val service = CleanerService(
        repository = CleanerRepositoryFake(),
        sanitizerStrategyExecutor = executor,
    )

    context("clean()") {

        should("remove tracking parameters") {
            val result =
                service.clean("https://www.some.site/?utm_source=source&utm_medium=medium&ga_source=ga_source&ga_medium=ga_medium")
            result should beInstanceOf<CleaningResult.Success>()
            (result as CleaningResult.Success).cleanedText should be("https://www.some.site/")
            result.cleanedParametersCount should be(4)
            result.urls should haveSize(1)
            result.urls.first() should be("https://www.some.site/")
        }

        should("keep non-tracking parameters") {
            val result =
                service.clean("https://www.some.site/?utm_source=source&utm_medium=medium&page=1&q=query")
            result should beInstanceOf<CleaningResult.Success>()
            (result as CleaningResult.Success).cleanedText should be("https://www.some.site/?page=1&q=query")
            result.cleanedParametersCount should be(2)
            result.urls should haveSize(1)
            result.urls.first() should be("https://www.some.site/?page=1&q=query")
        }

        should("clean multiple URLs in text") {
            val result =
                service.clean("Hey, I would like to share this https://www.some.site/?utm_source=source&utm_medium=medium link as well as this https://www.some2.site?wt_mc=wt_mc link :)")
            result should beInstanceOf<CleaningResult.Success>()
            (result as CleaningResult.Success).cleanedText should be("Hey, I would like to share this https://www.some.site/ link as well as this https://www.some2.site link :)")
            result.cleanedParametersCount should be(3)
            result.urls should haveSize(2)
            result.urls[0] should be("https://www.some.site/")
            result.urls[1] should be("https://www.some2.site")
        }
    }
})
