package com.example.daily_notes

import android.app.Application

class MyApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}