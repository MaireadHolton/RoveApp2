package ie.wit.donationx.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VisitModel(var id: Long = 0,
                         val visitTitle: String = "",
                         val visitType: String = "N/A",
                         val rating: Int = 0) : Parcelable