package com.bucai.torch.util.network;


import android.util.Log;

import com.bucai.torch.util.Apis;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleRetrofit {
    private static final String TAG = "Retrofit";

    private SingleRetrofit() {
    }

    private static class SingletonHolder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(Apis.ZZZIA)
                .client(new OkHttpClient().newBuilder()
                        .addInterceptor(new ResponseInterceptor())
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        return SingletonHolder.INSTANCE;
    }


    private static class ResponseInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {//log requests and responses
            Response response = chain.proceed(chain.request());
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            MediaType contentType = responseBody.contentType();
            Charset charset = Charset.forName("UTF-8");
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            Buffer buffer = source.buffer().clone();
            String content = buffer.readString(charset);
            Log.d(TAG, "request: " + chain.request().toString());
            Log.d(TAG, "response: " + response.toString());
            for (String s : content.split("\n")) {
                Log.v(TAG, "content: " + s);
            }
            return response;
        }
    }
}
