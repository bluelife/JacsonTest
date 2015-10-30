package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by slomka.jin on 2015/10/30.
 */
public class LoginService {
    private Retrofit adapter;
    private LoginApi loginApi;

    public LoginApi build(){
        ObjectMapper objectMapper=new ObjectMapper();
        SimpleModule simpleModule=new SimpleModule();
        simpleModule.addDeserializer(LoginStatus.class,new LoginConvert());
        objectMapper.registerModule(simpleModule);
        adapter=new Retrofit.Builder()
                .baseUrl(LoginApi.API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return  adapter.create(LoginApi.class);
    }
    public Retrofit getRetrofit(){
        return adapter;
    }
}
