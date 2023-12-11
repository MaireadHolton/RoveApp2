package ie.wit.donationx.ui.visit

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.donationx.R
import ie.wit.donationx.databinding.FragmentVisitBinding
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.ui.report.ReportViewModel

class VisitFragment : Fragment() {

    //lateinit var app: DonationXApp
    private var _fragBinding: FragmentVisitBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController
    private lateinit var visitViewModel: VisitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _fragBinding = FragmentVisitBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addVisit)
	    setupMenu()
        visitViewModel = ViewModelProvider(this).get(VisitViewModel::class.java)
        visitViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })


        fragBinding.visitTitle.getText().toString()


        //fragBinding.progressBar.max = 10000
        fragBinding.ratingPicker.minValue = 1
        fragBinding.ratingPicker.maxValue = 10

        fragBinding.ratingPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to ratingAmount
            fragBinding.ratingAmount.setText("$newVal")
        }
        setButtonListener(fragBinding)
        return root
    }

 private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_donate, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


//    companion object {
//        @JvmStatic
//        fun newInstance() =
//                VisitFragment().apply {
//                    arguments = Bundle().apply {}
//                }
//    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.visitError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentVisitBinding) {
        layout.addVisitButton.setOnClickListener {
            val visitTitle = fragBinding.visitTitle.getText().toString()
            val rating = if (layout.ratingAmount.text.isNotEmpty())
                layout.ratingAmount.text.toString().toInt() else layout.ratingPicker.value
                val visitType = if(layout.visitType.checkedRadioButtonId == R.id.Bar) "Bar"
                else if (layout.visitType.checkedRadioButtonId == R.id.Restaurant) "Restaurant"
                else if (layout.visitType.checkedRadioButtonId == R.id.Hotel) "Hotel"
                else if (layout.visitType.checkedRadioButtonId == R.id.Shop) "Shop"
                else "Museum"
                visitViewModel.addVisit(VisitModel(visitTitle = visitTitle, visitType = visitType,rating = rating))
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        reportViewModel.observableVisitsList.observe(viewLifecycleOwner, Observer {
        })
    }
}