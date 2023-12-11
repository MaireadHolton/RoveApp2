package ie.wit.donationx.models

import androidx.lifecycle.MutableLiveData

interface VisitStore {
    fun findAll(visitsList: MutableLiveData<List<VisitModel>>)
    fun findById(id: Long) : VisitModel?
    fun create(visit: VisitModel)
}