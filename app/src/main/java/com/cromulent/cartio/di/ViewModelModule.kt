package com.cromulent.cartio.di

import com.cromulent.cartio.viewmodel.ListPageViewModel
import com.cromulent.cartio.viewmodel.LoginScreenViewModel
import com.cromulent.cartio.viewmodel.SignupViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListPageViewModel(get(), get()) }
    viewModel { SignupViewModel(get(), get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
}