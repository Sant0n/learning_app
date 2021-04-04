package nnar.learning_app.userInterface.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.dataInterface.MobileRowView
import nnar.learning_app.databinding.MobileListRowBinding
import nnar.learning_app.domain.model.Mobile

class UserMobileRowView (itemView: View): RecyclerView.ViewHolder(itemView),
    MobileRowView {

    private var binding = MobileListRowBinding.bind(itemView)

    /** otro argumento para render cuando haya bbdd sera el usuario: currentUser: String */
    override fun render(mobile: Mobile) {
        itemView.setBackgroundColor( itemView.context.resources.getColor(R.color.white))
        renderMobileIcon(mobile.img_url)
        renderName(mobile.name)
        renderVersion(mobile.version)
        renderFavoriteIcon(mobile.isFavorite)

    }
/**
        itemView.isProjectFavorite.setOnClickListener {
            presenter.changeProjectFavorite(project, this, adapterPosition)
        }
    */

    override fun markAsSelected(){
        itemView.setBackgroundColor( itemView.context.resources.getColor(R.color.orange))
    }

    override fun markAsUnSelected(){
        itemView.background.clearColorFilter()
    }


    override fun markAsFavorite() {
        super.markAsFavorite()
        binding.isMobileFavorite.setColorFilter(itemView.context.resources.getColor(R.color.blue))
    }

    override fun removeAsFavorite() {
        super.removeAsFavorite()
        binding.isMobileFavorite.setColorFilter(itemView.context.resources.getColor(R.color.grey))
    }

    private fun renderMobileIcon(url: String){
        /**
        if (url == ""){
            Picasso.get().load(R.drawable.folder).resize(80,80).centerCrop().into(itemView.projectPicture)
        } else {
            Picasso.get().load(url).resize(80,80).centerCrop().into(itemView.projectPicture)
        }*/
    }

    private fun renderName(name: String) {
        binding.mobileName.text = name
    }

    private fun renderVersion(version: String){
        binding.mobileVersion.text = version
    }

    private fun renderFavoriteIcon(isFavorite: Boolean){
        /**
        presenter.checkFavorite(isFavorite)
        binding.isMobileFavorite.setOnClickListener {
            presenter.changeFavorite(isFavorite)
        }*/
    }

}