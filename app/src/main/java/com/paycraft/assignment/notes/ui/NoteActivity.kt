package com.paycraft.assignment.notes.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.paycraft.assignment.notes.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class NoteActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.container)

        fab.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_addFragment)
            hideFloatingButton()
        }
    }

    fun showFloatingButton(){
        fab.show()
        fab.visibility = View.VISIBLE
    }

    fun hideFloatingButton(){
        fab.hide()
        fab.visibility = View.GONE
    }
}