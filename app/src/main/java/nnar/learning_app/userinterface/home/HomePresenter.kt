package nnar.learning_app.userinterface.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import nnar.learning_app.data.repository.FirebaseContactRepository
import nnar.learning_app.domain.model.ContactViewHolder

class HomePresenter : ViewModel() {

    fun addContact() {
        FirebaseContactRepository.addContact()
    }

    fun setContactList(adapter: RecyclerView.Adapter<ContactViewHolder>) {
        viewModelScope.launch {
            if (FirebaseContactRepository.getCurrentContactsId())
                adapter.notifyDataSetChanged()
        }
    }
}