package ie.wit.donationx.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VisitModel(var _id: String = "N/A",
                         val visitTitle: String = "",
                         val visitType: String = "N/A",
                         val rating: Int = 0,
                         var email: String = "joe@bloggs.com") : Parcelable