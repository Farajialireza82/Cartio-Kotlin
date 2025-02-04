package com.cromulent.cartio.di

import com.cromulent.cartio.ui.screen.shop.list.ListPageViewModel
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenViewModel
import com.cromulent.cartio.ui.screen.auth.signup.SignupViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListPageViewModel(get(), get()) }
    viewModel { SignupViewModel(get(), get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
}