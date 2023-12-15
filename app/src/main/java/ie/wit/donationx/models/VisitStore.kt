package ie.wit.donationx.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface VisitStore {
    fun findAll(visitsList: MutableLiveData<List<VisitModel>>)
    fun findAll(userid: String, visitsList: MutableLiveData<List<VisitModel>>)
    fun findById(userid: String, visitid:String, visit: MutableLiveData<VisitModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, visit: VisitModel)
    fun delete(userid: String, visitid: String)
    fun update(userid:String, visitid: String, visit: VisitModel)
}