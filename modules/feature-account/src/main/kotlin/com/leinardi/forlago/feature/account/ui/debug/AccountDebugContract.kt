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

package com.leinardi.forlago.feature.account.ui.debug

import com.leinardi.forlago.core.ui.base.ViewEffect
import com.leinardi.forlago.core.ui.base.ViewEvent
import com.leinardi.forlago.core.ui.base.ViewState

object AccountDebugContract {
    data class State(
        val accountName: String?,
        val refreshToken: String?,
        val accessToken: String?,
        val accessTokenExpiry: Long?,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnGetAccessTokenClicked : Event()
        object OnInvalidateAccessTokenClicked : Event()
        object OnInvalidateRefreshTokenClicked : Event()
        object OnLogOutClicked : Event()
        object OnOpenSignInScreenClicked : Event()
        object OnViewAttached : Event()
        object OnViewDetached : Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
