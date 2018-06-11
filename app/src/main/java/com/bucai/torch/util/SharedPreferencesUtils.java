package com.bucai.torch.util;

/**
 * Created by zxzhu
 * 2018/5/25.
 * enjoy it !!
 */
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 * @author xiaanming
 *
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context , String key, Object object){

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T>T getParam(Context context , String key, T defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return (T) sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            Integer tem = sp.getInt(key, (Integer) defaultObject);
            return (T)tem;
        }
        else if("Boolean".equals(type)){
            Boolean tem = sp.getBoolean(key, (Boolean)defaultObject);
            return (T)tem;
        }
        else if("Float".equals(type)){
            Float tem = sp.getFloat(key, (Float)defaultObject);
            return (T)tem;
        }
        else if("Long".equals(type)){
            Long tem = sp.getLong(key, (Long)defaultObject);
            return (T)tem;
        }

        return null;
    }
}
