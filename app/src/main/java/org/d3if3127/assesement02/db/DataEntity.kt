package org.d3if3127.assesement02.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var umur: Float,
    var berat: Float,
    var tinggi: Float,
    var isMale: Boolean
)
