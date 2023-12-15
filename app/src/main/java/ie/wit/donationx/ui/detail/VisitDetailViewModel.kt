package ie.wit.donationx.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.donationx.firebase.FirebaseDBManager
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel
import timber.log.Timber

class VisitDetailViewModel : ViewModel() {
    private val visit = MutableLiveData<VisitModel>()

    val observableVisit: LiveData<VisitModel>
        get() = visit
        //set(value) {visit.value = value.value}

    fun getVisit(userid:String, id: String) {
        try {
            //VisitManager.findById(email, id, visit)
            FirebaseDBManager.findById(userid, id, visit)
            Timber.i("Detail getVisit() Success : ${
                visit.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDonation() Error : $e.message")
        }
    }

    fun updateVisit(userid:String, id: String,visit: VisitModel) {
        try {
            //VisitManager.update(email, id, visit)
            FirebaseDBManager.update(userid, id, visit)
            Timber.i("Detail update() Success : $visit")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}