package ie.wit.donationx.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class VisitModel(var uid: String = "",
                         val visitTitle: String = "",
                         val visitType: String = "N/A",
                         val rating: Int = 0,
                         var pic: String = "",
                         var email: String = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "visitTitle" to visitTitle,
            "visitType" to visitType,
            "rating" to rating,
            "pic" to pic,
            "email" to email
        )
    }
}