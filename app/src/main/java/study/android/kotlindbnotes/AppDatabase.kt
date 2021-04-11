package study.android.kotlindbnotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ResultEntity::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultsDao(): ResultsDao
}