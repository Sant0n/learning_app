package nnar.learning_app.domain.model

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.databinding.ItemContactBinding

class ContactViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

    private var binding = ItemContactBinding.bind(listItemView)

    fun setNameTextView(text: String) {
        binding.contactName.text = text
    }

    fun getStateButton(): Button {
        return binding.stateButton
    }

    fun getRemoveButton(): Button {
        return binding.removeContact
    }

    fun getSeeMore(): ImageButton {
        return binding.seeMore
    }

    fun setButtonState(text: String, state: Boolean) {
        binding.stateButton.text = text
        binding.stateButton.isEnabled = state
    }
}