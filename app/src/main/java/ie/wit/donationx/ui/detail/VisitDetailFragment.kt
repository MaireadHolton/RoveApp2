package ie.wit.donationx.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ie.wit.donationx.R
import ie.wit.donationx.databinding.FragmentVisitDetailBinding
import ie.wit.donationx.models.VisitModel

class VisitDetailFragment : Fragment() {

    private lateinit var detailViewModel: VisitDetailViewModel
    private val args by navArgs<VisitDetailFragmentArgs>()
    private var _fragBinding: FragmentVisitDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentVisitDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(VisitDetailViewModel::class.java)
        detailViewModel.observableVisit.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render(/*visit: VisitModel*/) {
        fragBinding.editTitle.setText("Business name")
        //fragBinding.editRating.setText(visit.rating.toString())
        //fragBinding.editVisitType.setText (visit.visitType.toString())
        fragBinding.visitvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getVisit(args.visitid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}