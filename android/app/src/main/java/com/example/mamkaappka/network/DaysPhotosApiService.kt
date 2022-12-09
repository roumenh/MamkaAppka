package com.example.mamkaappka.network

//import com.example.gogglebookssearch.BuildConfig
import com.example.mamkaappka.network.DayPhoto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "http://rh.xf.cz/api/"
//private const val API_KEY = BuildConfig.apiKey

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DaysPhotosApiService {

    @GET("list/{date}")
    suspend fun getDayPhotos(
        @Path("date") date : String
    ) : List<DayPhoto>

    @GET("day_photo/{date}")
    suspend fun getTodayPhoto(
        @Path("date") date : String
    ) : DayPhoto



}

object DaysPhotosApi {
    val retrofitService: DaysPhotosApiService by lazy {
        retrofit.create(DaysPhotosApiService::class.java)
}
}