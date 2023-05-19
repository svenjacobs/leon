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

package com.svenjacobs.app.leon.startup

import android.content.Context
import com.svenjacobs.app.leon.core.domain.inject.DomainContainer
import com.svenjacobs.app.leon.core.domain.sanitizer.aliexpress.AliexpressSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.amazon.AmazonProductSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.amazon.AmazonSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.aol.AolSearchSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.change.ChangeSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.ebay.EbaySanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.elfinanciero.ElFinancieroSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.emptyparameters.EmptyParametersSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.facebook.FacebookSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.flipkart.FlipkartSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.georiot.GeoRiotSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.google.GoogleAdsSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.google.GoogleAnalyticsSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.google.GoogleSearchSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.instagram.InstagramSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.jdoqocy.JdoqocySanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.lazada.LazadaSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.linksynergy.LinkSynergySanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.netflix.NetflixSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.newegg.NewEggSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.sessionids.SessionIdsSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.shopee.ShopeeSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.spiegel.SpiegelSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.spotify.SpotifySanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.twitter.TwitterSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.webtrekk.WebtrekkSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.yahoo.YahooSearchSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.youtube.YoutubeMusicSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.youtube.YoutubeRedirectSanitizer
import com.svenjacobs.app.leon.core.domain.sanitizer.youtube.YoutubeShortUrlSanitizer
import com.svenjacobs.app.leon.sanitizer.SanitizerRepositoryImpl
import kotlinx.collections.immutable.persistentListOf

class ContainerInitializer : DistinctInitializer<Unit> {

	override fun create(context: Context) {
		DomainContainer.init(
			appContext = context,
			sanitizerRepositoryProvider = { SanitizerRepositoryImpl() },
			sanitizers = persistentListOf(
				AliexpressSanitizer(),
				AmazonProductSanitizer(),
				AmazonSanitizer(),
				AolSearchSanitizer(),
				ChangeSanitizer(),
				EbaySanitizer(),
				ElFinancieroSanitizer(),
				EmptyParametersSanitizer(),
				FacebookSanitizer(),
				FlipkartSanitizer(),
				GeoRiotSanitizer(),
				GoogleAdsSanitizer(),
				GoogleAnalyticsSanitizer(),
				GoogleSearchSanitizer(),
				InstagramSanitizer(),
				JdoqocySanitizer(),
				LazadaSanitizer(),
				LinkSynergySanitizer(),
				NetflixSanitizer(),
				NewEggSanitizer(),
				SessionIdsSanitizer(),
				ShopeeSanitizer(),
				SpiegelSanitizer(),
				SpotifySanitizer(),
				TwitterSanitizer(),
				WebtrekkSanitizer(),
				YahooSearchSanitizer(),
				YoutubeMusicSanitizer(),
				YoutubeRedirectSanitizer(),
				YoutubeShortUrlSanitizer(),
			),
		)
	}
}
