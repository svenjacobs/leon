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

package com.svenjacobs.app.leon.service

import com.svenjacobs.app.leon.domain.Defaults.Flipkart
import com.svenjacobs.app.leon.test.MOCK_SANITIZER_STRATEGY_EXECUTOR
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.be
import io.kotest.matchers.should

class FlipkartSanitizerTest : ShouldSpec({

    context("sanitize") {

        should("remove Flipkart parameters") {
            val result = MOCK_SANITIZER_STRATEGY_EXECUTOR.sanitize(
                Flipkart,
                "https://www.flipkart.com/msi-gf63-thin-core-i5-10th-gen-8-gb-512-gb-ssd-windows-10-home-4-graphics-nvidia-geforce-rtx-3050-144-hz-10uc-607in-gaming-laptop/p/itm92565651dc3ed?pid=COMG5AWT9CKRMEFU&lid=LSTCOMG5AWT9CKRMEFULV38QB&marketplace=FLIPKART&q=gaming+laptop&store=6bo%2Fb5g&srno=s_1_15&otracker=AS_QueryStore_OrganicAutoSuggest_1_9_na_na_na&otracker1=AS_QueryStore_OrganicAutoSuggest_1_9_na_na_na&fm=SEARCH&iid=22ff2dee-d44e-40e3-9811-5d229fa974fc.COMG5AWT9CKRMEFU.SEARCH&ppt=hp&ppn=homepage&ssid=zv1f42w4bs3azitc1635436210945&qH=da5ee6f53d84b3c2"
            )

            result.artifactsRemoved should be(14)
            result.output should be("https://www.flipkart.com/msi-gf63-thin-core-i5-10th-gen-8-gb-512-gb-ssd-windows-10-home-4-graphics-nvidia-geforce-rtx-3050-144-hz-10uc-607in-gaming-laptop/p/itm92565651dc3ed")
        }
    }
})
