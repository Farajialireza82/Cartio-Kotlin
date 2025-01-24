package com.cromulent.cartio.di

import com.cromulent.cartio.viewmodel.ListPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListPageViewModel(get()) }
}