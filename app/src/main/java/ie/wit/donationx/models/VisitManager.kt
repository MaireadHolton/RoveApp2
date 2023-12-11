package ie.wit.donationx.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object VisitManager : VisitStore {

    private val visits = ArrayList<VisitModel>()

    override fun findAll(): List<VisitModel> {
        return visits
    }

    override fun findById(id:Long) : VisitModel? {
        val foundVisit: VisitModel? = visits.find { it.id == id }
        return foundVisit
    }

    override fun create(visit: VisitModel) {
        visit.id = getId()
        visits.add(visit)
        logAll()
    }

    fun logAll() {
        Timber.v("** Visits List **")
        visits.forEach { Timber.v("Visit ${it}") }
    }
}