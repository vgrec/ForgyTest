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

package com.leinardi.forlago.core.encryption.interactor

import com.leinardi.forlago.core.android.coroutine.CoroutineDispatchers
import com.leinardi.forlago.core.encryption.CryptoHelper
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.security.GeneralSecurityException
import javax.inject.Inject

class DecryptInteractor @Inject constructor(
    private val cryptoHelper: CryptoHelper,
    private val dispatchers: CoroutineDispatchers,
) {
    suspend operator fun invoke(plainText: String): String? =
        withContext(dispatchers.io) {
            try {
                cryptoHelper.decrypt(plainText)
            } catch (e: GeneralSecurityException) {
                Timber.e(e)
                null
            }
        }

    suspend operator fun invoke(plainText: ByteArray): ByteArray? =
        withContext(dispatchers.io) {
            try {
                cryptoHelper.decrypt(plainText)
            } catch (e: GeneralSecurityException) {
                Timber.e(e)
                null
            }
        }
}
