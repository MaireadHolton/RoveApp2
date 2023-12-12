package ie.wit.donationx.models

import androidx.lifecycle.MutableLiveData
import ie.wit.donationx.api.VisitClient
import ie.wit.donationx.api.VisitWrapper
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import retrofit2.Callback

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object VisitManager : VisitStore {

    private var visits = ArrayList<VisitModel>()

    override fun findAll(visitsList: MutableLiveData<List<VisitModel>>) {

        val call = VisitClient.getApi().findAll()

        call.enqueue(object : retrofit2.Callback<List<VisitModel>> {
            override fun onResponse(call: Call<List<VisitModel>>,
                                    response: Response<List<VisitModel>>
            ) {
                visitsList.value = response.body() as ArrayList<VisitModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<VisitModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, visitsList: MutableLiveData<List<VisitModel>>) {
        val call = VisitClient.getApi().findAll(email)

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

    override fun findById(id:String) : VisitModel? {
        val foundVisit: VisitModel? = visits.find { it._id == id}
        return foundVisit
    }

    override fun create(visit: VisitModel) {

        val call = VisitClient.getApi().post(visit.email,visit)

        call.enqueue(object : Callback<VisitWrapper>{
            override fun onResponse(call: Call<VisitWrapper>,
                                    response: Response<VisitWrapper>
            ) {
                val visitWrapper = response.body()
                if (visitWrapper != null) {
                    Timber.i("Retrofit ${visitWrapper.message}")
                    Timber.i("Retrofit ${visitWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<VisitWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(email: String, id: String) {
        val call = VisitClient.getApi().delete(email, id)

        call.enqueue(object : Callback<VisitWrapper> {
            override fun onResponse(call: Call<VisitWrapper>,
                                    response: Response<VisitWrapper>
            ) {
                val visitWrapper = response.body()
                if (visitWrapper != null) {
                    Timber.i("Retrofit Delete ${visitWrapper.message}")
                    Timber.i("Retrofit Delete ${visitWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<VisitWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

}
