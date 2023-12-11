package ie.wit.donationx.models

interface VisitStore {
    fun findAll() : List<VisitModel>
    fun findById(id: Long) : VisitModel?
    fun create(visit: VisitModel)
}