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
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.domain.usecase.ContactUseCase
import nnar.learning_app.ui.contactCreation.ContactCreationActivity
import nnar.learning_app.ui.contactDetail.ContactDetailActivity
import nnar.learning_app.ui.mainmenu.MainMenuActivity

class ContactListActivity: AppCompatActivity(), ContactListView {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var presenter:ContactListHomePresenter
    private lateinit var addButton: FloatingActionButton
    private lateinit var adapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        addButton = binding.addContactButton
        recyclerView = binding.listmenuRecyclerView

        adapter = ContactListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        presenter = ContactListHomePresenter(this, ContactUseCase(ContactRepository()))
        presenter.fetchContacts()

        setListeners()
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            /**val intent = Intent(it.context, ContactCreationActivity()::class.java)
            it.context.startActivity(intent)**/
            presenter.addNewContact()
            adapter.notifyDataSetChanged()

        }
    }

    override fun getList(contactList: MutableSet<Contact>) {
        adapter.updateData(contactList)
        adapter.notifyDataSetChanged()
    }

    override fun showMessageContactAdded(s: String) {
        //adapter.notifyDataSetChanged()
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showMessageContactDeleted(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}

