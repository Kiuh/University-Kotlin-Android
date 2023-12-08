package by.bsuir.khimich.boolib.database.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.database.daos.WhiteListDao
import by.bsuir.khimich.boolib.database.entities.BookEntity
import by.bsuir.khimich.boolib.database.entities.WhiteListEntity

@Database(entities = [BookEntity::class, WhiteListEntity::class], version = 3)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun whiteListDao(): WhiteListDao
}

internal fun AppDataBase(context: Context) = Room.databaseBuilder(
    context,
    AppDataBase::class.java,
    "books_database"
)
    .fallbackToDestructiveMigration()
    .build()
