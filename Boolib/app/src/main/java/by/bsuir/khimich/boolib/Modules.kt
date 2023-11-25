package by.bsuir.khimich.boolib

import by.bsuir.khimich.boolib.database.core.AppDataBase
import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.datasources.BooksDataSource
import by.bsuir.khimich.boolib.datasources.BooksDataSourceImpl
import by.bsuir.khimich.boolib.datasources.RemoteBooksDataSource
import by.bsuir.khimich.boolib.datasources.RemoteBooksDataSourceImpl
import by.bsuir.khimich.boolib.models.HomeViewModel
import by.bsuir.khimich.boolib.models.SiteViewModel
import by.bsuir.khimich.boolib.models.UpsertViewModel
import by.bsuir.khimich.boolib.repositories.BooksRepository
import by.bsuir.khimich.boolib.repositories.BooksRepositoryImpl
import by.bsuir.khimich.boolib.repositories.SiteBooksRepository
import by.bsuir.khimich.boolib.repositories.SiteBooksRepositoryImpl
import io.ktor.client.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<AppDataBase> { AppDataBase(context = get()) }
    single<BookDao> { get<AppDataBase>().bookDao() }
}

val siteDataBaseModule = module {
    single<HttpClient> { httpClientBuilder(json) }
}

val booksRepositoryModule = module {
    includes(databaseModule)

    single<BooksDataSource> { BooksDataSourceImpl(get<BookDao>()) }
    single<BooksRepository> { BooksRepositoryImpl(get<BooksDataSource>()) }
}

val siteBooksRepositoryModule = module {
    includes(siteDataBaseModule)

    single<RemoteBooksDataSource> { RemoteBooksDataSourceImpl(get<HttpClient>()) }
    single<SiteBooksRepository> { SiteBooksRepositoryImpl(get<RemoteBooksDataSource>()) }
}

val appModule = module {
    includes(booksRepositoryModule)
    includes(siteBooksRepositoryModule)

    viewModel { HomeViewModel(get<BooksRepository>()) }
    viewModel { UpsertViewModel(get<BooksRepository>()) }
    viewModel { SiteViewModel(get<SiteBooksRepository>(), get<BooksRepository>()) }
}
