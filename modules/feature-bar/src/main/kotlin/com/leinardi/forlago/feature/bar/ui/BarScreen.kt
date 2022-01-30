/*
 * Copyright 2022 Roberto Leinardi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leinardi.forlago.feature.bar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leinardi.forlago.core.ui.component.TopAppBar
import com.leinardi.forlago.feature.bar.R
import com.leinardi.forlago.feature.bar.ui.BarContract.Event
import com.leinardi.forlago.feature.bar.ui.BarContract.State

@Composable
fun BarScreen(viewModel: BarViewModel = hiltViewModel()) {
    BarScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
fun BarScreen(
    state: State,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.i18n_bar_screen_title),
                navigateUp = { sendEvent(Event.OnUpButtonClicked) },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        stringResource(R.string.i18n_bar_text_received, state.text),
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = typography.h4,
                    )
                }
                Button(
                    onClick = { sendEvent(Event.OnBackButtonClicked) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.i18n_back))
                }
            }
        },
    )
}

@Preview
@Composable
fun BarScreenPreview() {
    BarScreen(State("Preview"), {})
}
