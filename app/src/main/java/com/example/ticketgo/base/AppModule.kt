package com.example.ticketgo.base

import android.app.Application
import androidx.room.Room
import com.example.ticketgo.database.AppDataBase
import com.example.ticketgo.ui.MainViewModel
import com.example.ticketgo.ui.event_type.EventTypeDao
import com.example.ticketgo.ui.events.EventDao
import com.example.ticketgo.ui.events.EventViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { EventViewModel(get(), get()) }
}

val UserDB = module {
    fun provideDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDataBase::class.java,
            "event_database"
        ).build()
    }

    fun provideEventDao(appDataBase: AppDataBase): EventDao {
        return appDataBase.getEventDao()
    }

    fun provideEventCategoryDao(appDataBase: AppDataBase): EventTypeDao {
        return appDataBase.getEventCategoryDao()
    }

    single { provideDataBase(androidApplication()) }
    single { provideEventDao(get()) }
    single { provideEventCategoryDao(get()) }
}