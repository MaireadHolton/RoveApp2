package ie.wit.donationx.ui.visit

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import ie.wit.donationx.R
import ie.wit.donationx.databinding.FragmentVisitBinding
import ie.wit.donationx.firebase.FirebaseDBManager
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.donationx.ui.map.MapsViewModel
import ie.wit.donationx.ui.report.ReportViewModel
import ie.wit.donationx.utils.showImagePicker
import timber.log.Timber

class VisitFragment : Fragment() {

    private var _fragBinding: FragmentVisitBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController
    private lateinit var visitViewModel: VisitViewModel
    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val mapsViewModel: MapsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _fragBinding = FragmentVisitBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addVisit)
        setupMenu()
        visitViewModel = ViewModelProvider(this).get(VisitViewModel::class.java)
        visitViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })


        fragBinding.visitTitle.getText().toString()

        setButtonListener(fragBinding)
        return root
    }


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
            val rating =  if (layout.ratingAmount.text.isNotEmpty())
                layout.ratingAmount.text.toString().toFloat() else layout.ratingPicker.rating.toString().toFloat()
            val visitType = if(layout.visitType.checkedRadioButtonId == R.id.Bar) "Bar"
            else if (layout.visitType.checkedRadioButtonId == R.id.Restaurant) "Restaurant"
            else if (layout.visitType.checkedRadioButtonId == R.id.Hotel) "Hotel"
            else if (layout.visitType.checkedRadioButtonId == R.id.Shop) "Shop"
            else "Museum"


            visitViewModel.addVisit(loggedInViewModel.liveFirebaseUser,
                VisitModel(visitTitle = visitTitle, visitType = visitType,
                    rating = rating, email = loggedInViewModel.liveFirebaseUser.value?.email!!,
                    latitude = mapsViewModel.currentLocation.value!!.latitude,
                    longitude = mapsViewModel.currentLocation.value!!.longitude))
        }
    }


 private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_visit, menu)
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


