package nnar.learning_app.userInterface.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.userInterface.mobileDetails.MobileDetailsActivity


class UserMobilesAdapter(private val rmButton: ImageButton) : RecyclerView.Adapter<UserMobileRowView>() {

    private var presenter = MobileListPresenter()

    fun updateData(mList: List<Mobile>) {
        presenter.updateData(mList)
        notifyDataSetChanged()
    }

    fun addMobile(mobile: Mobile){
        presenter.addMobile(mobile)
        notifyDataSetChanged()
    }

    fun deleteSelected(){
        presenter.removeItems()
        notifyDataSetChanged()
        rmButton.visibility = View.GONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMobileRowView {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.mobile_list_row, parent, false)
        return UserMobileRowView(itemView)
    }

    override fun getItemCount(): Int = presenter.getItemCount()

    override fun onBindViewHolder(holder: UserMobileRowView, position: Int) {
        presenter.onBindMobileRowViewAtPosition(position, holder)

        holder.itemView.setOnClickListener {
            presenter.getMobile(position)
            val intent = Intent(holder.itemView.context, MobileDetailsActivity::class.java)
            intent.putExtra("mobile", presenter.getMobile(position))
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val empty = presenter.checkLongSelected(position, holder)
            if(empty){
                rmButton.visibility = View.GONE
            } else {
                rmButton.visibility = View.VISIBLE
            }
            true
        }
    }


}

