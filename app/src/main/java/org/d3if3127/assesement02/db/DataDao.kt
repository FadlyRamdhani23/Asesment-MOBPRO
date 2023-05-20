package org.d3if3127.assesement02.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    fun insert(data: DataEntity)

    //masih perlu di pelajari
//    @Query("DELETE FROM data WHERE id= :id")
//    fun deleteSingleData(id: Int)
    @Query("DELETE FROM data")
    fun clearData()
    @Query("SELECT * FROM data ORDER BY id DESC")
    fun getLastData(): LiveData<List<DataEntity>>
}