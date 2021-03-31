package nnar.learning_app.ui.contactListMenu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityContactListBinding
import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.usecase.ContactUseCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity

class ContactListActivity: AppCompatActivity(), ContactListView {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var adapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val presenter = ContactListPresenter(this, ContactUseCase(ContactRepository()))

        addButton = binding.addContactButton
        recyclerView = binding.listmenuRecyclerView
        adapter = ContactListAdapter(presenter.fetchContacts())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        setListeners(presenter)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setListeners(presenter: ContactListPresenter) {
        addButton.setOnClickListener {
            presenter.addNewContact()
        }

        recyclerView.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showMessageContactAdded(s: String) {
        adapter.notifyDataSetChanged()
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showMessageContactDeleted(s: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToContactDetail(){

    }

}

