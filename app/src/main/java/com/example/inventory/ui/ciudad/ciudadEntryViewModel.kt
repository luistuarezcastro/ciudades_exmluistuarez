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

package com.example.inventory.ui.ciudad

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ciudad
import com.example.inventory.data.ciudadRepository

/**
 * ViewModel to validate and insert items in the Room database.
 */
class ItemEntryViewModel(private val itemsRepository: ciudadRepository) : ViewModel() {

    /**
     * Holds current ciudad ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(ciudadDetails: ciudadDetails) {
        itemUiState =
            ItemUiState(ciudadDetails = ciudadDetails, isEntryValid = validateInput(ciudadDetails))
    }

    /**
     * Inserts an [ciudad] in the Room database
     */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertciudad(itemUiState.ciudadDetails.toItem())
        }
    }

    private fun validateInput(uiState: ciudadDetails = itemUiState.ciudadDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && pais.isNotBlank() && provincia.isNotBlank() && codigo_postal.isNotBlank()
        }
    }
}

data class ItemUiState(
    val ciudadDetails: ciudadDetails = ciudadDetails(),
    val isEntryValid: Boolean = false
)

data class ciudadDetails(
    val id: Int = 0,
    val name: String = "",
    val pais: String = "",
    val provincia: String = "",
    val codigo_postal: String = "",
)


fun ciudadDetails.toItem(): ciudad = ciudad(
    id = id,
    name = name,
    pais = pais,
    provincia = provincia,
    codigo_postal = codigo_postal.toIntOrNull() ?: 0
)

fun ciudad.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    ciudadDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)


fun ciudad.toItemDetails(): ciudadDetails = ciudadDetails(
    id = id,
    name = name,
    pais = pais,
    provincia = provincia,
    codigo_postal = codigo_postal.toString()
)
