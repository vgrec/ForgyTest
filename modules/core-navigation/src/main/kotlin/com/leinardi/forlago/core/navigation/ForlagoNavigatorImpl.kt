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

package com.leinardi.forlago.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ForlagoNavigatorImpl @Inject constructor() : ForlagoNavigator {
    // A capacity > 0 is required to not lose an event sent before the nav host starts collecting (e.g. Add account from System settings)
    private val navigationEvents = Channel<NavigatorEvent>(capacity = Channel.CONFLATED)

    override val destinations = navigationEvents.receiveAsFlow()

    override fun navigateUp(): Boolean = navigationEvents.trySend(NavigatorEvent.NavigateUp).isSuccess

    override fun navigateHome(): Boolean = navigate(ForlagoNavigator.HOME_DESTINATION_ROUTE) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }

    override fun navigateBack(): Boolean = navigationEvents.trySend(NavigatorEvent.NavigateBack).isSuccess

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit): Boolean =
        navigationEvents.trySend(NavigatorEvent.Directions(route, builder)).isSuccess
}
