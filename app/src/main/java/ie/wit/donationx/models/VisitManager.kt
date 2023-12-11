package ie.wit.donationx.models

import androidx.lifecycle.MutableLiveData
import ie.wit.donationx.api.VisitClient
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import javax.security.auth.callback.Callback

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object VisitManager : VisitStore {

    private val visits = ArrayList<VisitModel>()

    override fun findAll(visitsList: MutableLiveData<List<VisitModel>>) {
        val call = VisitClient.getApi().getall()

        call.enqueue(object : retrofit2.Callback<List<VisitModel>> {
                override fun onResponse(
                    call: Call<List<VisitModel>>,
                    response: Response<List<VisitModel>>,
                ) {
                    visitsList.value = response.body() as ArrayList<VisitModel>
                    Timber.i("Retrofit JSON = ${response.body()}")
                }

                override fun onFailure(call: Call<List<VisitModel>>, t: Throwable) {
                    Timber.i("Retrofit Error : $t.message")
                }
            })
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
