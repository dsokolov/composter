package ru.composter.passanger.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 15.10.2016.
 */

public class ApiSingleton {

    static ApiSingleton apiSingleton;

    private Api api;

    private ApiSingleton() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bigvill.elizar9l.bget.ru/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    };

    public static ApiSingleton instance() {
        if (apiSingleton == null) {
            apiSingleton = new ApiSingleton();
        }
        return apiSingleton;
    }

    public Api getApi() {
        return api;
    }
}
