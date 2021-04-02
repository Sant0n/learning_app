package nnar.learning_app.data.repository

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R

class ContactViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

    // Your holder should contain and initialize a member variable
    // for any view that will be set as you render a row
    private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.contact_name)
    private val stateButton: Button = itemView.findViewById<Button>(R.id.state_button)
    private val removeButton: Button = itemView.findViewById<Button>(R.id.remove_contact)
    private val seeMore: ImageButton = itemView.findViewById<ImageButton>(R.id.see_more)

    fun setNameTextView(text: String) {
        nameTextView.text = text
    }

    fun getStateButton(): Button {
        return stateButton
    }

    fun getRemoveButton(): Button {
        return removeButton
    }

    fun getSeeMore(): ImageButton {
        return seeMore
    }

    fun setButtonState(text: String, state: Boolean) {
        stateButton.text = text
        stateButton.isEnabled = state
    }
}