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

package com.svenjacobs.app.leon.startup

import android.content.Context
import com.svenjacobs.app.leon.core.domain.inject.AppContainer
import com.svenjacobs.app.leon.sanitizer.SanitizerRepositoryImpl
import com.svenjacobs.app.leon.sanitizer.amazon.AmazonProductSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.amazon.AmazonSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.amazon.AmazonSmileSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.aol.AolSearchSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.ebay.EbaySanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.emptyparameters.EmptyParametersSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.facebook.FacebookSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.flipkart.FlipkartSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.google.GoogleAnalyticsSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.google.GoogleSearchSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.instagram.InstagramSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.netflix.NetflixSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.sessionids.SessionIdsSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.spotify.SpotifySanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.twitter.TwitterSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.webtrekk.WebtrekkSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.yahoo.YahooSearchSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.youtube.YoutubeRedirectSanitizerRegistration
import com.svenjacobs.app.leon.sanitizer.youtube.YoutubeShortUrlSanitizerRegistration
import kotlinx.collections.immutable.persistentListOf

class ContainerInitializer : DistinctInitializer<Unit> {

	override fun create(context: Context) {
		AppContainer.init(
			appContext = context,
			sanitizerRepositoryProvider = { SanitizerRepositoryImpl() },
			sanitizerRegistrations = persistentListOf(
				AmazonProductSanitizerRegistration(),
				AmazonSanitizerRegistration(),
				AmazonSmileSanitizerRegistration(),
				AolSearchSanitizerRegistration(),
				EbaySanitizerRegistration(),
				EmptyParametersSanitizerRegistration(),
				FacebookSanitizerRegistration(),
				FlipkartSanitizerRegistration(),
				GoogleAnalyticsSanitizerRegistration(),
				GoogleSearchSanitizerRegistration(),
				InstagramSanitizerRegistration(),
				NetflixSanitizerRegistration(),
				SessionIdsSanitizerRegistration(),
				SpotifySanitizerRegistration(),
				TwitterSanitizerRegistration(),
				WebtrekkSanitizerRegistration(),
				YahooSearchSanitizerRegistration(),
				YoutubeRedirectSanitizerRegistration(),
				YoutubeShortUrlSanitizerRegistration(),
			),
		)
	}
}
