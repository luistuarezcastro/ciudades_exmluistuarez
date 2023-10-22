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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ciudadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve, update and delete an almacen from the [ciudadRepository]'s data source.
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ciudadRepository,
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])


    val uiState: StateFlow<ItemDetailsUiState> =
        itemsRepository.getciudadStream(itemId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(outOfStock = it.codigo_postal <= 0, ciudadDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )

    /**
     * Reduces the ciudad quantity by one and update the repository
     */


    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.ciudadDetails.toItem()
            if (currentItem.codigo_postal > 0) {
                itemsRepository.updateciudad(currentItem.copy(codigo_postal = currentItem.codigo_postal - 1))
            }
        }
    }

    /**
     * Deletes the almacen from the [ciudadRepository]'s data source.
     */
    suspend fun deleteItem() {
        itemsRepository.deleteciudad(uiState.value.ciudadDetails.toItem())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val ciudadDetails: ciudadDetails = ciudadDetails()
)
