package com.nokopi.marketregistersystem.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDatabaseDao {

    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM product_info_table")
    fun get(): LiveData<List<Product>>

}