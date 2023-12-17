package ie.wit.donationx.ui.visit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.donationx.firebase.FirebaseDBManager
import ie.wit.donationx.firebase.FirebaseImageManager
import ie.wit.donationx.models.VisitModel

class VisitViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addVisit(firebaseUser: MutableLiveData<FirebaseUser>, visit: VisitModel) {
        visit.pic = FirebaseImageManager.imageUri.value.toString()
        status.value = try {
            //VisitManager.create(visit)
            FirebaseDBManager.create(firebaseUser, visit)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}