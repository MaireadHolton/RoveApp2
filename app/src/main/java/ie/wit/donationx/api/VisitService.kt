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
    fun findAll(): Call<List<VisitModel>>

    @GET("/visits/{email}")
    fun findAll(@Path("email") email: String?): Call<List<VisitModel>>

    @GET("/visits/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<VisitModel>

    @DELETE("/visits/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<VisitWrapper>

    @POST("/visits/{email}")
    fun post(@Path("email") email: String?,
             @Body visit: VisitModel): Call<VisitWrapper>

    @PUT("/visits/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body visit: VisitModel
    ): Call<VisitWrapper>
}