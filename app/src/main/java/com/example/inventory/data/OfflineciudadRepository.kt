/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class OfflineciudadRepository(private val itemDao: ciudadDao) : ciudadRepository {
    // mostar las ciudades
    override fun getAllciudadStream(): Flow<List<ciudad>> = itemDao.getAllciudad()

    //mostrar ciudad por id
    override fun getciudadStream(id: Int): Flow<ciudad?> = itemDao.getciudad(id)

    //insertar ciudad
    override suspend fun insertciudad(item: ciudad) = itemDao.insert(item)

    //borrar ciudad
    override suspend fun deleteciudad(item: ciudad) = itemDao.delete(item)

    //actualizar la ciudad
    override suspend fun updateciudad(item: ciudad) = itemDao.update(item)
}
