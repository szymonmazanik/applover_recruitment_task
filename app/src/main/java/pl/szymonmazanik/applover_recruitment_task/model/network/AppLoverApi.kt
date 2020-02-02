package pl.szymonmazanik.applover_recruitment_task.model.network

import io.reactivex.Single
import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest
import pl.szymonmazanik.applover_recruitment_task.model.entity.response.LoginResponse
import pl.szymonmazanik.applover_recruitment_task.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AppLoverApi {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    companion object {
        /**
         * Provides [AppLoverApi] instance
         */
        fun getService(): AppLoverApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(AppLoverApi::class.java)
        }
    }
}