package com.bucai.torch.util.network

import com.bucai.torch.bean.Location
import com.bucai.torch.util.Apis
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by zxzhu
 *   2018/6/22.
 *   enjoy it !!
 */
interface Services {
    @GET(Apis.GET_CITY)
    fun getCity(@Query("longitude") longitude: String, @Query("latitude") latitude: String): Observable<Location>
}