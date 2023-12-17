package ie.wit.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.donationx.firebase.FirebaseDBManager
import ie.wit.donationx.models.VisitModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val visitsList = MutableLiveData<List<VisitModel>>()
    var readOnly = MutableLiveData(false)

    val observableVisitsList: LiveData<List<VisitModel>>
        get() = visitsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            //VisitManager.findAll(liveFirebaseUser.value?.email!!, visitsList)
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, visitsList)
            Timber.i("Report Load Success : ${visitsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(visitsList)
            Timber.i("Report LoadAll Success : ${visitsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun delete(uid: String, id: String,) {
        try {
            //VisitManager.delete(userid,id)
            FirebaseDBManager.delete(uid, id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}