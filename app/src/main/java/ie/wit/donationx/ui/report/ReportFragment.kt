package ie.wit.donationx.ui.report

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.donationx.R
import ie.wit.donationx.adapters.VisitAdapter
import ie.wit.donationx.adapters.VisitClickListener
import ie.wit.donationx.databinding.FragmentReportBinding
import ie.wit.donationx.main.Rove2App
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.donationx.utils.*

class ReportFragment : Fragment(), VisitClickListener {

    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToVisitFragment()
            findNavController().navigate(action)
        }


        showLoader(loader,"Downloading Visits")
        reportViewModel.observableVisitsList.observe(viewLifecycleOwner, Observer {
                visits ->
            visits?.let {
                render(visits as ArrayList<VisitModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Visit")
                val adapter = fragBinding.recyclerView.adapter as VisitAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                reportViewModel.delete(reportViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as VisitModel).uid!!)
                hideLoader(loader)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onVisitClick(viewHolder.itemView.tag as VisitModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

   private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_report, menu)

                val item = menu.findItem(R.id.toggleVisits) as MenuItem
                item.setActionView(R.layout.togglebutton_layout)
                val toggleVisits: SwitchCompat = item.actionView!!.findViewById(R.id.toggleButton)
                toggleVisits.isChecked = false

                toggleVisits.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) reportViewModel.loadAll()
                    else reportViewModel.load()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(visitsList: ArrayList<VisitModel>) {
        fragBinding.recyclerView.adapter = VisitAdapter(visitsList,this,
            reportViewModel.readOnly.value!!)

        if (visitsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.visitsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.visitsNotFound.visibility = View.GONE
        }
    }

    override fun onVisitClick(visit: VisitModel) {
        val action = ReportFragmentDirections.actionReportFragmentToVisitDetailFragment(visit.uid)
        if(!reportViewModel.readOnly.value!!)
            findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Visits")
            if(reportViewModel.readOnly.value!!)
                reportViewModel.loadAll()
            else
                reportViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Visits")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                reportViewModel.liveFirebaseUser.value = firebaseUser
                reportViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}