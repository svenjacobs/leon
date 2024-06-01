/*
 * Léon - The URL Cleaner
 * Copyright (C) 2024 Sven Jacobs
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

package com.svenjacobs.app.leon

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.svenjacobs.app.leon.core.domain.CleanerService
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.M)
class ProcessTextActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
		val readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)

		when {
			// If readonly, delegate to MainActivity
			readonly -> startActivity(
				Intent(this, MainActivity::class.java).apply {
					action = Intent.ACTION_SEND
					type = "text/plain"
					putExtra(Intent.EXTRA_TEXT, text)
				},
			)

			text.isNullOrBlank() -> setResult(RESULT_CANCELED)

			// Needs to run with runBlocking or else setResult() won't work
			else -> runBlocking {
				val result = CleanerService().clean(text)

				setResult(
					RESULT_OK,
					Intent().apply {
						putExtra(Intent.EXTRA_PROCESS_TEXT, result.cleanedText)
					},
				)
			}
		}

		finish()
	}
}
