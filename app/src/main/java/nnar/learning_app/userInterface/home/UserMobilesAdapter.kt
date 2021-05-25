package nnar.learning_app.userInterface.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.userInterface.mobileDetails.MobileDetailsActivity


class UserMobilesAdapter(private val presenter: HomePresenter, val x: HomeActivity) : RecyclerView.Adapter<UserMobileRowView>() {

    fun updateData() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMobileRowView {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.mobile_list_row, parent, false)
        return UserMobileRowView(itemView)
    }

    override fun getItemCount(): Int = presenter.getItemCount()

    override fun onBindViewHolder(holder: UserMobileRowView, position: Int) {
        val mobile = presenter.getMobileAtPosition(position)
        holder.render(mobile)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MobileDetailsActivity::class.java)
            intent.putExtra("mobile", mobile)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            presenter.checkOnLongSelected(position, holder)
            true
        }
    }


}

