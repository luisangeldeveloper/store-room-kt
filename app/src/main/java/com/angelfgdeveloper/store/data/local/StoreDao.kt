package com.angelfgdeveloper.store.data.local

import androidx.room.*
import com.angelfgdeveloper.store.data.model.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    suspend fun getAllStores(): MutableList<StoreEntity>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    suspend fun addStore(storeEntity: StoreEntity) : Long

    @Update
    suspend fun updateStore(storeEntity: StoreEntity)

    @Delete
    suspend fun deleteStore(storeEntity: StoreEntity)
}