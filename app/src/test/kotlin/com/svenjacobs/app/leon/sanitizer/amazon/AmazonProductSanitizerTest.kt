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

package com.svenjacobs.app.leon.sanitizer.amazon

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class AmazonProductSanitizerTest : WordSpec(
	{

		"invoke" should {

			"remove various Amazon parameters" {
				val sanitizer = AmazonProductSanitizer()
				val result = sanitizer(
					"https://www.amazon.de/Xiaomi-Aktivit%C3%A4tstracker-Trainings-Puls%C3%" +
						"BCberwachung-Akkulaufzeit/dp/B091G3FLL7/?_encoding=UTF8&pd_rd_w=xDcJP&pf" +
						"_rd_p=bf172aca-3277-41f6-babb-6ce7fc34cf7f&pf_rd_r=ZC6FZ5G6W9K8DEZTPBYW&" +
						"pd_rd_r=11b3ec4e-047c-4f37-8302-62dedb8f502b&pd_rd_wg=Ozi90&ref_=pd_gw_c" +
						"i_mcx_mr_hp_atf_m",
				)

				result shouldBe "https://www.amazon.de/Xiaomi-Aktivit%C3%A4tstracker-Trainings-Pu" +
					"ls%C3%BCberwachung-Akkulaufzeit/dp/B091G3FLL7/"
			}
		}
	},
)
