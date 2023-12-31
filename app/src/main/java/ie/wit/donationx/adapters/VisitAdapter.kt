package ie.wit.donationx.adapters

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.ToggleButton
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import ie.wit.donationx.R
import ie.wit.donationx.databinding.CardVisitBinding
import ie.wit.donationx.firebase.FirebaseDBManager
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.utils.customTransformation
import timber.log.Timber

interface VisitClickListener {
    fun onVisitClick(visit: VisitModel)
}

class VisitAdapter constructor(private var visits: ArrayList<VisitModel>,
                               private val listener: VisitClickListener,
                               private val readOnly: Boolean)
    : RecyclerView.Adapter<VisitAdapter.MainHolder>() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var isFavourite = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVisitBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        val button = binding.favBtn

        return MainHolder(binding, readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val visit = visits[holder.adapterPosition]
        holder.bind(visit,listener)

        /*holder.itemView.findViewById<ToggleButton>(R.id.favBtn).isChecked = visit.isFavourite

        holder.itemView.findViewById<ToggleButton>(R.id.favBtn).setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    holder.itemView.context,
                    "Added to Favourites",
                    Toast.LENGTH_SHORT).show()
                visit.isFavourite = true
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Removed from Favorites",
                    Toast.LENGTH_SHORT).show()
                visit.isFavourite = false
            }
        }*/
    }

    fun removeAt(position: Int) {
        visits.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = visits.size

    inner class MainHolder(val binding : CardVisitBinding, private val  readOnly: Boolean) :
                            RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly


        fun bind(visit: VisitModel, listener: VisitClickListener) {

            binding.root.tag = visit
            binding.visit = visit
            Picasso.get().load(visit.pic.toUri(0)
                .resize(200,200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onVisitClick(visit) }
            binding.executePendingBindings()

        }

        private fun checkIsFavourite(visitModel: VisitModel){
            Log.d(ContentValues.TAG, "checkIsFavourite: Checking if favourite")
            val visitTitle = visitModel.visitTitle
            FirebaseDBManager.database.child(firebaseAuth.uid!!).child("Favourites").child(visitTitle)
                .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        isFavourite = snapshot.exists()
                        if (isFavourite){
                            binding.favBtn.text = "Remove"
                        }
                        else {
                           binding.favBtn.text = "Add"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }

        private fun addToFavourites(visitModel: VisitModel){
            val hashMap = HashMap<String, Any>()
            val visitTitle = visitModel.visitTitle
            hashMap["visitId"] = visitTitle
            FirebaseDBManager.database.child(firebaseAuth.uid!!).child("Favourites").child(visitTitle)
                .setValue(hashMap)
                .addOnSuccessListener {

                   Timber.i("Added to Favourites")
                }
                .addOnFailureListener{
                        e->
                    Timber.i("Add to Favourites error")
                }
        }

        private fun removeFromFavourites(visitModel: VisitModel){
            val visitTitle = visitModel.visitTitle
            FirebaseDBManager.database.child(firebaseAuth.uid!!).child("Favourites").child(visitTitle)
                .removeValue()
                .addOnSuccessListener {
                    Timber.i("Removed from Favourites")
                }
                .addOnFailureListener{e->
                    Timber.i("Error: not removed from Favourites")
                }

        }
    }
}
