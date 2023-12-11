package ie.wit.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.donationx.models.VisitManager
import ie.wit.donationx.models.VisitModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val visitsList = MutableLiveData<List<VisitModel>>()

    val observableVisitsList: LiveData<List<VisitModel>>
        get() = visitsList

    init {
        load()
    }

    fun load() {
        try {
            VisitManager.findAll(visitsList)
            Timber.i("Retrofit Success : $visitsList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }
}