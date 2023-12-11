package ie.wit.donationx.api

import ie.wit.donationx.models.VisitModel
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Body
interface VisitService {
    @GET("/visits")
    fun getall(): Call<List<VisitModel>>

    @GET("/visits/{id}")
    fun get(@Path("id") id: String): Call<VisitModel>

    @DELETE("/visits/{id}")
    fun delete(@Path("id") id: String): Call<VisitWrapper>

    @POST("/visits")
    fun post(@Body visit: VisitModel): Call<VisitWrapper>

    @PUT("/visits/{id}")
    fun put(@Path("id") id: String,
            @Body visit: VisitModel
    ): Call<VisitWrapper>
}