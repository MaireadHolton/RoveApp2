package ie.wit.donationx.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel

class VisitDetailViewModel : ViewModel() {
    private val visit = MutableLiveData<VisitModel>()

    val observableVisit: LiveData<VisitModel>
        get() = visit
    fun getVisit(id: Long) {
        visit.value = VisitManager.findById(id)
    }
}