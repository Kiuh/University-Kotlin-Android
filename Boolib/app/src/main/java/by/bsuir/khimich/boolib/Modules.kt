package by.bsuir.khimich.boolib

import by.bsuir.khimich.boolib.database.core.AppDataBase
import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.datasources.BooksDataSource
import by.bsuir.khimich.boolib.datasources.BooksDataSourceImpl
import by.bsuir.khimich.boolib.datasources.RemoteBooksDataSource
import by.bsuir.khimich.boolib.datasources.RemoteBooksDataSourceImpl
import by.bsuir.khimich.boolib.models.HomeViewModel
import by.bsuir.khimich.boolib.models.UpsertViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.BooksRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { AppDataBase(context = get()) }
    single<BookDao> { get<AppDataBase>().bookDao() }
}

val appModule = module {
    includes(databaseModule)

    single<BooksDataSource> { BooksDataSourceImpl(get<BookDao>()) }
    single<RemoteBooksDataSource> { RemoteBooksDataSourceImpl(httpClientBuilder(json)) }
    single<BooksRepository> { BooksRepositoryImpl(get<BooksDataSource>()) }

    viewModel { HomeViewModel(get<BooksRepository>()) }
    viewModel { UpsertViewModel(get<BooksRepository>()) }
}
