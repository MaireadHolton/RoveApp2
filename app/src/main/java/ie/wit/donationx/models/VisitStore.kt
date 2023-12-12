package ie.wit.donationx.models

import androidx.lifecycle.MutableLiveData

interface VisitStore {
    fun findAll(visitsList: MutableLiveData<List<VisitModel>>)
    fun findAll(email: String, visitsList: MutableLiveData<List<VisitModel>>)
    fun findById(id: String) : VisitModel?
    fun create(visit: VisitModel)
    fun delete(email: String,id: String)
}