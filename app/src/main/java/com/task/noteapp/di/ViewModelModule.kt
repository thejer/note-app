package com.task.noteapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: NoteAppViewModelFactory): ViewModelProvider.Factory

    // TODO Add other ViewModels.
}
