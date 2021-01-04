package com.paycraft.assignment.notes.ui.notelist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.di.ViewModelProviderFactory
import com.paycraft.assignment.notes.ui.NoteActivity
import com.paycraft.assignment.notes.ui.NoteViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class NoteListFragment : DaggerFragment(),
    NoteAdapter.Interaction {

    private var isList: Boolean = true
    private lateinit var noteAdapter: NoteAdapter

    private lateinit var noteViewModel: NoteViewModel

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var allNotes: List<Note>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allNotes = mutableListOf()
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        noteViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(NoteViewModel::class.java)
        initRecyclerView()
        observerLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list, menu)
        val menuList = menu.findItem(R.id.action_list)
        val menuGrid = menu.findItem(R.id.action_grid)
        if (isList) {
            menuList.isVisible = false
            menuGrid.isVisible = true
        } else {
            menuList.isVisible = true
            menuGrid.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                recyclerView.apply {
                    isList=true
                    layoutManager = LinearLayoutManager(this@NoteListFragment.context)
                    adapter?.notifyDataSetChanged()
                    invalidateOptionsMenu(activity)
                }
            }
            R.id.action_grid -> {
                recyclerView.apply {
                    isList=false
                    layoutManager = GridLayoutManager(this@NoteListFragment.context,2)
                    adapter?.notifyDataSetChanged()
                    invalidateOptionsMenu(activity)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observerLiveData() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner, Observer { lisOfNotes ->
            lisOfNotes?.let {
                allNotes = it
                noteAdapter.swap(it)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            noteAdapter = NoteAdapter(
                allNotes,
                this@NoteListFragment
            )
            layoutManager = LinearLayoutManager(this@NoteListFragment.context)
            adapter = noteAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(recyclerView)
        }
    }

    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        //Swipe recycler view items on RIGHT
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                noteViewModel.delete(allNotes[position])
                Toast.makeText(activity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(position: Int, item: Note) {
        (activity as NoteActivity?)?.hideFloatingButton()
        val navDirection = NoteListFragmentDirections.actionListFragmentToEditFragment(item)
        findNavController().navigate(navDirection)
    }
}


