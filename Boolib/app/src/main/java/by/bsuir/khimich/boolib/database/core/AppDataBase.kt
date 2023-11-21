package by.bsuir.khimich.boolib.database.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.database.entities.BookEntity

@Database(entities = [BookEntity::class], version = 1)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

internal fun AppDataBase(context: Context) = Room.databaseBuilder(
    context,
    AppDataBase::class.java,
    "books_database"
)
    .fallbackToDestructiveMigration()
    .build()
