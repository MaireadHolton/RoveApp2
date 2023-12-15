package ie.wit.donationx.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.donationx.models.VisitModel
import ie.wit.donationx.models.VisitStore
import timber.log.Timber

object FirebaseDBManager : VisitStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(visitsList: MutableLiveData<List<VisitModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, visitsList: MutableLiveData<List<VisitModel>>) {
        database.child("user-visits").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Rove error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<VisitModel>()
                    val children = snapshot.children
                    children.forEach {
                        val visit = it.getValue(VisitModel::class.java)
                        localList.add(visit!!)
                    }
                    database.child("user-visits").child(userid)
                        .removeEventListener(this)

                    visitsList.value = localList
                }
            })
    }

    override fun findById(userid: String, visitid: String, visit: MutableLiveData<VisitModel>) {
        database.child("user-visits").child(userid)
            .child(visitid).get().addOnSuccessListener {
                visit.value = it.getValue(VisitModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, visit: VisitModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("visits").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        visit.uid = key
        val visitValues = visit.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/visits/$key"] = visitValues
        childAdd["/user-visits/$uid/$key"] = visitValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, visitid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/visits/$visitid"] = null
        childDelete["/user-visits/$userid/$visitid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, visitid: String, visit: VisitModel) {
        val visitValues = visit.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["visits/$visitid"] = visitValues
        childUpdate["user-visits/$userid/$visitid"] = visitValues

        database.updateChildren(childUpdate)
    }
}