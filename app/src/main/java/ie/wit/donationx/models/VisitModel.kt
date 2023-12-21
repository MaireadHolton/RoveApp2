package ie.wit.donationx.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class VisitModel(var uid: String = "",
                      val visitTitle: String = "",
                      val visitType: String = "N/A",
                      val rating: Float = 0.0F,
                      var pic: String = "",
                      var isFavourite: Boolean = false,
                      var latitude: Double = 0.0,
                      var longitude: Double = 0.0,
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
            "isFavourite" to isFavourite,
            "latitude" to latitude,
            "longitude" to longitude,
            "email" to email
        )
    }
}