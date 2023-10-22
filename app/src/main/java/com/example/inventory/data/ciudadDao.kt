
package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ciudadDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllciudad(): Flow<List<ciudad>>

    @Query("SELECT * from items WHERE id = :id")
    fun getciudad(id: Int): Flow<ciudad>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing ciudad into the database Room ignores the conflict.

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ciudad)

    @Update
    suspend fun update(item: ciudad)

    @Delete
    suspend fun delete(item: ciudad)
}
