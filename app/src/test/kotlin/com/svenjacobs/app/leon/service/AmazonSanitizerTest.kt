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

import com.svenjacobs.app.leon.domain.Defaults.Amazon
import com.svenjacobs.app.leon.test.MOCK_SANITIZER_STRATEGY_EXECUTOR
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.be
import io.kotest.matchers.should

class AmazonSanitizerTest : ShouldSpec({

    context("sanitize") {

        should("remove various Amazon parameters") {
            val result = MOCK_SANITIZER_STRATEGY_EXECUTOR.sanitize(
                Amazon,
                "https://www.amazon.de/Xiaomi-Aktivit%C3%A4tstracker-Trainings-Puls%C3%BCberwachung-Akkulaufzeit/dp/B091G3FLL7/?_encoding=UTF8&pd_rd_w=xDcJP&pf_rd_p=bf172aca-3277-41f6-babb-6ce7fc34cf7f&pf_rd_r=ZC6FZ5G6W9K8DEZTPBYW&pd_rd_r=11b3ec4e-047c-4f37-8302-62dedb8f502b&pd_rd_wg=Ozi90&ref_=pd_gw_ci_mcx_mr_hp_atf_m"
            )

            result.artifactsRemoved should be(7)
            result.output should be("https://www.amazon.de/Xiaomi-Aktivit%C3%A4tstracker-Trainings-Puls%C3%BCberwachung-Akkulaufzeit/dp/B091G3FLL7/")
        }
    }
})
