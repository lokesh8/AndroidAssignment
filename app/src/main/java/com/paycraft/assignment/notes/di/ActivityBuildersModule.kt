package com.paycraft.assignment.notes.di

import com.paycraft.assignment.notes.ui.NoteActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class,FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): NoteActivity

}