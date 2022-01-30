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

package com.leinardi.forlago.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.leinardi.forlago.core.navigation.ForlagoNavigator
import com.leinardi.forlago.core.navigation.NavigatorEvent
import com.leinardi.forlago.core.ui.theme.ForlagoTheme
import com.leinardi.forlago.navigation.addComposableDestinations
import com.leinardi.forlago.navigation.addDialogDestinations
import com.leinardi.forlago.ui.MainContract.Event.OnIntentReceived
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var forlagoNavigator: ForlagoNavigator

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navHostController: NavHostController = rememberNavController().also { this.navHostController = it }
            ForlagoTheme {
                ForlagoScaffold(
                    startDestination = viewModel.viewState.value.startDestination,
                    navHostController = navHostController,
                    forlagoNavigator = forlagoNavigator,
                )
            }
        }
        viewModel.onUiEvent(OnIntentReceived(intent))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        viewModel.onUiEvent(OnIntentReceived(intent))
        navHostController.handleDeepLink(intent)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}

@Composable
fun ForlagoScaffold(
    navHostController: NavHostController,
    forlagoNavigator: ForlagoNavigator,
    startDestination: String,
) {
    val activity = LocalContext.current as Activity
    LaunchedEffect(navHostController) {
        forlagoNavigator.destinations.onEach {
            when (val event = it) {
                is NavigatorEvent.NavigateUp -> Timber.d("NavigateUp successful = ${navHostController.navigateUp()}")
                is NavigatorEvent.NavigateBack -> activity.onBackPressed()
                is NavigatorEvent.Directions -> navHostController.navigate(
                    event.destination,
                    event.builder,
                )
            }
        }.launchIn(this)
    }

    NavHost(
        // check https://google.github.io/accompanist/navigation-animation/
        navController = navHostController,
        startDestination = startDestination,
        builder = {
            addComposableDestinations()
            addDialogDestinations()
        },
    )
}
