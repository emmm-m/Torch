package com.bucai.torch.util.network

import com.bucai.torch.bean.Location
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zxzhu
 *   2018/6/22.
 *   enjoy it !!
 */
class HttpUtil {
    private var services: Services? = null

    fun getCity(longitude: Double, latitude: Double): Observable<Location> {
        if (services == null) {
            services = SingleRetrofit.getRetrofit().create(Services::class.java)
        }
        return services!!.getCity(longitude, latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

}