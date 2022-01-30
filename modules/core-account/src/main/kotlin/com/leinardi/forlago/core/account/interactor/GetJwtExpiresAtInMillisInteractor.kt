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

package com.leinardi.forlago.core.account.interactor

import com.google.crypto.tink.subtle.Base64
import com.leinardi.forlago.core.account.model.Jwt
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetJwtExpiresAtInMillisInteractor @Inject constructor(
    private val json: Json,
) {
    operator fun invoke(jwt: String): Long {
        val payloadEncoded = jwt.split('.')[1]
        val payloadJson = String(Base64.decode(payloadEncoded, Base64.DEFAULT))
        val jwtPayload = json.decodeFromString<Jwt.Payload>(payloadJson)
        return TimeUnit.SECONDS.toMillis(jwtPayload.exp)
    }
}
