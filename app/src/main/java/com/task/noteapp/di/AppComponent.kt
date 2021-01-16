package com.task.noteapp.di

import android.app.Application
import com.task.noteapp.notes.NotesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, LocalStorageModule::class])
interface AppComponent {

    fun inject(target: NotesFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
