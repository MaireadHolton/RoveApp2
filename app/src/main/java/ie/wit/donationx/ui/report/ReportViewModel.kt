package ie.wit.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val visitsList = MutableLiveData<List<VisitModel>>()

    val observableVisitsList: LiveData<List<VisitModel>>
        get() = visitsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            VisitManager.findAll(liveFirebaseUser.value?.email!!, visitsList)
            Timber.i("Report Load Success : ${visitsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            VisitManager.delete(email,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}