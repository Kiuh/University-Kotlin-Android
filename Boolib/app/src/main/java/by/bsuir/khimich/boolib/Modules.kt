package by.bsuir.khimich.boolib

import by.bsuir.khimich.boolib.database.core.AppDataBase
import by.bsuir.khimich.boolib.database.daos.BookDao
import by.bsuir.khimich.boolib.database.daos.WhiteListDao
import by.bsuir.khimich.boolib.datasources.*
import by.bsuir.khimich.boolib.models.*
import by.bsuir.khimich.boolib.repositories.*
import io.ktor.client.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<AppDataBase> { AppDataBase(context = get()) }
    single<BookDao> { get<AppDataBase>().bookDao() }
    single<WhiteListDao> { get<AppDataBase>().whiteListDao() }
}

val siteDataBaseModule = module {
    single<HttpClient> { httpClientBuilder(json) }
}

val whiteListRepositoryModule = module {
    includes(databaseModule)

    single<WhiteListDataSource> { WhiteListDataSourceImpl(get<WhiteListDao>()) }
    single<WhiteListRepository> { WhiteListRepositoryImpl(get<WhiteListDataSource>()) }
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
    includes(whiteListRepositoryModule)

    viewModel { HomeViewModel(get<BooksRepository>()) }
    viewModel { UpsertViewModel(get<BooksRepository>()) }
    viewModel { SiteViewModel(get<SiteBooksRepository>(), get<BooksRepository>()) }
    viewModel { OverviewViewModel(it.getOrNull(), OverviewMode.FromLocal, get(), get(), get()) }
}
