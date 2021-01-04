package com.paycraft.assignment.notes.di


import com.paycraft.assignment.notes.ui.addnote.AddNoteFragment
import com.paycraft.assignment.notes.ui.editnote.EditFragment
import com.paycraft.assignment.notes.ui.notelist.NoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): NoteListFragment

    @ContributesAndroidInjector
    abstract fun contributeAddFragment(): AddNoteFragment

    @ContributesAndroidInjector
    abstract fun contributeEditFragment(): EditFragment
}