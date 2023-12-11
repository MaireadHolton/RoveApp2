package ie.wit.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.donationx.R
import ie.wit.donationx.databinding.CardVisitBinding
import ie.wit.donationx.models.VisitModel

interface VisitClickListener {
    fun onVisitClick(visit: VisitModel)
}

class VisitAdapter constructor(private var visits: List<VisitModel>,
                                  private val listener: VisitClickListener)
    : RecyclerView.Adapter<VisitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVisitBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val visit = visits[holder.adapterPosition]
        holder.bind(visit,listener)
    }

    override fun getItemCount(): Int = visits.size

    inner class MainHolder(val binding : CardVisitBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(visit: VisitModel, listener: VisitClickListener) {
//            binding.ratingamount.text = visit.ratingamount.toString()
//            binding.visitType.text = visit.visitType

            binding.visit = visit
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onVisitClick(visit) }
            binding.executePendingBindings()
        }
    }
}