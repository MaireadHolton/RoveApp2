package ie.wit.donationx.ui.report

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.donationx.R
import ie.wit.donationx.adapters.VisitAdapter
import ie.wit.donationx.adapters.VisitClickListener
import ie.wit.donationx.databinding.FragmentReportBinding
import ie.wit.donationx.main.Rove2App
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.utils.createLoader
import ie.wit.donationx.utils.showLoader

class ReportFragment : Fragment(), VisitClickListener {

    lateinit var app: Rove2App
    lateinit var loader : AlertDialog
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //activity?.title = getString(R.string.action_report)
        loader = createLoader(requireActivity())

	setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        showLoader(loader,"Downloading Visits")
        reportViewModel.observableVisitsList.observe(viewLifecycleOwner, Observer {
                visits ->
            visits?.let { render(visits) }
        })


        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToVisitFragment()
            findNavController().navigate(action)
        }
        return root
    }

   private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_report, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(visitsList: List<VisitModel>) {
        fragBinding.recyclerView.adapter = VisitAdapter(visitsList,this)
        if (visitsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.visitsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.visitsNotFound.visibility = View.GONE
        }
    }

    override fun onVisitClick(visit: VisitModel) {
        val action = ReportFragmentDirections.actionReportFragmentToVisitDetailFragment(visit.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        reportViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}