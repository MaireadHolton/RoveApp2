package ie.wit.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel

class ReportViewModel : ViewModel() {

    private val visitsList = MutableLiveData<List<VisitModel>>()

    val observableVisitsList: LiveData<List<VisitModel>>
        get() = visitsList

    init {
        load()
    }

    fun load() {
        visitsList.value = VisitManager.findAll()
    }
}