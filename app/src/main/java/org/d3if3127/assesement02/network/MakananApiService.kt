package org.d3if3127.assesement02.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3127.assesement02.model.Food
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/FadlyRamdhani23/Asesment-MOBPRO/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface MakananApiService {
    @GET("food.json")
    suspend fun getMakanan(): List<Food>
}
object MakananApi {
    val service: MakananApiService by lazy {
        retrofit.create(MakananApiService::class.java)
    }
    fun getMakananUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }
