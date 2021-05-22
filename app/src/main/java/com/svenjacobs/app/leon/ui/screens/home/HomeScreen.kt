package com.svenjacobs.app.leon.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.services.model.CleaningResult

@Composable
fun HomeScreen(
    result: CleaningResult,
    onShareButtonClick: (CleaningResult.Success) -> Unit,
    onVerifyButtonClick: (CleaningResult.Success) -> Unit,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
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
                        .width(128.dp)
                        .height(128.dp)
                        .padding(
                            top = 16.dp,
                            bottom = 32.dp,
                        )
                )

                result.let { res ->
                    when (res) {
                        is CleaningResult.Success -> SuccessBody(
                            result = res,
                            onShareButtonClick = onShareButtonClick,
                            onVerifyButtonClick = onVerifyButtonClick,
                        )
                        is CleaningResult.Failure -> FailureBody()
                    }
                }
            }
        }
    }
}

@Composable
private fun Statistics(result: CleaningResult.Success) {
    Column {
        CounterText(
            result.cleanedParametersCount,
            pluralsRes = R.plurals.statistics_parameters,
            style = MaterialTheme.typography.h6,
        )
    }
}

@Composable
private fun SuccessBody(
    result: CleaningResult.Success,
    onShareButtonClick: (CleaningResult.Success) -> Unit,
    onVerifyButtonClick: (CleaningResult.Success) -> Unit,
) {
    Column {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = result.cleanedText,
                )

                Statistics(result)

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
                            style = MaterialTheme.typography.button,
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = { onVerifyButtonClick(result) }
                    ) {
                        Text(
                            text = stringResource(R.string.verify),
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FailureBody() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.how_to_title),
                style = MaterialTheme.typography.h6,
            )

            Row {
                Image(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(end = 16.dp),
                    painter = painterResource(R.drawable.howto_pixel_5),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Justify,
                    text = stringResource(id = R.string.how_to_text)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSuccess() {
    SuccessBody(
        result = CleaningResult.Success(
            originalText = "http://www.some.url?tracking=true",
            cleanedText = "http://www.some.url",
            cleanedParametersCount = 1,
            urls = emptyList(),
        ),
        onShareButtonClick = {},
        onVerifyButtonClick = {},
    )
}
