package ie.wit.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.donationx.R
import ie.wit.donationx.databinding.CardVisitBinding
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.utils.customTransformation

interface VisitClickListener {
    fun onVisitClick(visit: VisitModel)
}

class VisitAdapter constructor(private var visits: ArrayList<VisitModel>,
                               private val listener: VisitClickListener,
                               private val readOnly: Boolean)
    : RecyclerView.Adapter<VisitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVisitBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding, readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val visit = visits[holder.adapterPosition]
        holder.bind(visit,listener)
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
            Picasso.get().load(visit.pic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onVisitClick(visit) }
            binding.executePendingBindings()

        }
    }
}