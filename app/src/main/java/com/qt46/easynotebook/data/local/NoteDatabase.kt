package com.qt46.easynotebook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteItem

@Database(
    entities = [
        Note::class,
    NoteCategory::class,
    NoteItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun pdfDao(): NoteDao
    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null
        fun getDatabase(context: Context):NoteDatabase{

            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_db").fallbackToDestructiveMigration().build()
                INSTANCE=instance
                return instance
            }
        }
    }
}