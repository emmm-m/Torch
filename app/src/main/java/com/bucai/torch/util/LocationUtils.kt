package com.bucai.torch.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.zia.toastex.ToastEx

@Suppress("DEPRECATED_IDENTITY_EQUALS")
/**
 * Created by zxzhu
 *   2018/6/21.
 *   enjoy it !!
 */
class LocationUtils private constructor(private val mContext: Context) {
    private val TAG = "LocationUtilsTest"
    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    private var location: Location? = null

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    private var locationListener: LocationListener = object : LocationListener {

        /**
         * 当某个位置提供者的状态发生改变时
         */
        override fun onStatusChanged(provider: String, status: Int, arg2: Bundle) {

        }

        /**
         * 某个设备打开时
         */
        override fun onProviderEnabled(provider: String) {

        }

        /**
         * 某个设备关闭时
         */
        override fun onProviderDisabled(provider: String) {

        }

        /**
         * 手机位置发生变动
         */
        override fun onLocationChanged(location: Location) {
            location.accuracy//精确度
            setLocation(location)
        }
    }

    init {
        getLocation()
    }

    private fun getLocation() {
        //1.获取位置管理器
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //2.获取位置提供器，GPS或是NetWork
        val providers = locationManager!!.getProviders(true)
        locationProvider = when {
            providers.contains(LocationManager.NETWORK_PROVIDER) -> {
                //如果是网络定位
                Log.d(TAG, "如果是网络定位")
                LocationManager.NETWORK_PROVIDER
            }
            providers.contains(LocationManager.GPS_PROVIDER) -> {
                //如果是GPS定位
                Log.d(TAG, "如果是GPS定位")
                LocationManager.GPS_PROVIDER
            }
            else -> {
                Log.d(TAG, "没有可用的位置提供器")
                return
            }
        }
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            return
        }
        //3.获取上次的位置，一般第一次运行，此值为null
        val location = locationManager!!.getLastKnownLocation(locationProvider)
        if (location != null) {
            setLocation(location!!)
        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager!!.requestLocationUpdates(locationProvider, 0, 0f, locationListener)
    }

    private fun setLocation(location: Location) {
        this.location = location
        val address = "纬度：" + location.latitude + "经度：" + location.longitude
        Log.d(TAG, address)
    }

    //获取经度
    fun getLatitude(): Double? {
        if (location == null) {
            ToastEx.error(mContext, "请打开GPS功能")
            return 0.00
        }
        return location!!.latitude
    }

    //获取经度
    fun getLongitude(): Double? {
        if (location == null) {
            ToastEx.error(mContext, "请打开GPS功能")
            return 0.00
        }
        return location!!.longitude
    }

    // 移除定位监听
    fun removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (locationManager != null) {
            uniqueInstance = null
            locationManager!!.removeUpdates(locationListener)
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var uniqueInstance: LocationUtils? = null

        //采用Double CheckLock(DCL)实现单例
        fun getInstance(context: Context): LocationUtils? {
            if (uniqueInstance == null) {
                synchronized(LocationUtils::class.java) {
                    if (uniqueInstance == null) {
                        uniqueInstance = LocationUtils(context)
                    }
                }
            }
            return uniqueInstance
        }
    }

}