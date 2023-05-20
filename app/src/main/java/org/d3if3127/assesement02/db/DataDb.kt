package org.d3if3127.assesement02.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1, exportSchema = false)
abstract class DataDb : RoomDatabase() {
    abstract val dao: DataDao
    companion object {
        @Volatile
        private var INSTANCE: DataDb? = null
        fun getInstance(context: Context): DataDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataDb::class.java,
                        "data.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}