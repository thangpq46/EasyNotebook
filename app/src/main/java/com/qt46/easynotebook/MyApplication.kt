package com.qt46.easynotebook

import android.app.Application
import android.content.Context
import com.qt46.easynotebook.constants.NoteCategorys
import com.qt46.easynotebook.data.NoteRepository
import com.qt46.easynotebook.data.local.LocalDataSource
import com.qt46.easynotebook.data.local.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication: Application() {
    lateinit var repository:NoteRepository
    override fun onCreate() {
        super.onCreate()
        repository=NoteRepository(LocalDataSource(applicationContext,NoteDatabase.getDatabase(applicationContext).pdfDao()))




    }
}