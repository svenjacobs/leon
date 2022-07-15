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

package com.svenjacobs.app.leon.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.core.common.result.UiResult
import com.svenjacobs.app.leon.core.domain.CleanerService.Result
import com.svenjacobs.app.leon.ui.screens.home.model.HomeScreenViewModel
import com.svenjacobs.app.leon.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(),
    result: UiResult<Result>,
    showSnackbarMessage: (String) -> Unit,
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    fun onShareButtonClick(cleaned: Result) {
        val intent = viewModel.buildIntent(cleaned.cleanedText)
        context.startActivity(intent)
    }

    fun onVerifyButtonClick(result: Result) {
        val intent = viewModel.buildCustomTabIntent(context)
        intent.launchUrl(context, Uri.parse(result.urls.first()))
    }

    fun onCopyToClipboardClick(result: Result) {
        clipboard.setText(AnnotatedString(result.cleanedText))
        showSnackbarMessage(context.getString(R.string.clipboard_message))
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BroomIcon(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(
                            top = 16.dp,
                            bottom = 32.dp,
                        )
                )

                when (result) {
                    is UiResult.Success -> SuccessBody(
                        result = result.data,
                        onShareButtonClick = ::onShareButtonClick,
                        onVerifyButtonClick = ::onVerifyButtonClick,
                        onCopyToClipboardClick = ::onCopyToClipboardClick,
                    )
                    is UiResult.Error -> ErrorBody()
                    else -> {}
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SuccessBody(
    modifier: Modifier = Modifier,
    result: Result,
    onShareButtonClick: (Result) -> Unit,
    onCopyToClipboardClick: (Result) -> Unit,
    onVerifyButtonClick: (Result) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.original_url),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = result.originalText,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.cleaned_url),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = result.cleanedText,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = { onShareButtonClick(result) },
                    ) {
                        Text(
                            text = stringResource(R.string.share),
                        )
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = { onCopyToClipboardClick(result) },
                    ) {
                        Text(
                            text = stringResource(R.string.copy),
                        )
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = { onVerifyButtonClick(result) },
                    ) {
                        Text(
                            text = stringResource(R.string.verify),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ErrorBody(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.how_to_title),
                style = MaterialTheme.typography.headlineSmall,
            )

            Row {
                Image(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(end = 16.dp),
                    painter = painterResource(R.drawable.howto_pixel_5),
                    contentDescription = stringResource(R.string.a11y_howto)
                )

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Justify,
                    text = stringResource(R.string.how_to_text)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessBodyPreview() {
    AppTheme {
        SuccessBody(
            result = Result(
                originalText = "http://www.some.url?tracking=true",
                cleanedText = "http://www.some.url",
                urls = emptyList(),
            ),
            onShareButtonClick = {},
            onVerifyButtonClick = {},
            onCopyToClipboardClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FailureBodyPreview() {
    AppTheme {
        ErrorBody()
    }
}
