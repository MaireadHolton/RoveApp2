package ie.wit.donationx.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import ie.wit.donationx.R

class VisitDetailFragment : Fragment() {

    companion object {
        fun newInstance() = VisitDetailFragment()
    }

    private lateinit var viewModel: VisitDetailViewModel
    private val args by navArgs<VisitDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_visit_detail, container, false)

        Toast.makeText(context,"Visit ID Selected : ${args.visitid}",Toast.LENGTH_LONG).show()

        return view
    }


}