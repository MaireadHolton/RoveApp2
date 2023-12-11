package ie.wit.donationx.ui.visit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel

class VisitViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addVisit(visit: VisitModel) {
        status.value = try {
            VisitManager.create(visit)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}