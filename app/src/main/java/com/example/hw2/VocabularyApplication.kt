package com.example.hw2

import android.app.Application
import com.example.hw2.data.ItemRoomDatabase

class VocabularyApplication:Application() {
    val database:ItemRoomDatabase by lazy{ItemRoomDatabase.getDatabase(this)}
}