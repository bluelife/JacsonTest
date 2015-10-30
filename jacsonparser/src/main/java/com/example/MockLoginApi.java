package com.example;

import java.util.concurrent.TimeUnit;

import retrofit.Retrofit;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by slomka.jin on 2015/10/30.
 */
public class MockLoginApi implements LoginApi {

    private Retrofit retrofit;

    public MockLoginApi(Retrofit retrofit){
        this.retrofit=retrofit;
    }

    @Override
    public Observable<LoginStatus> login(@Query("username") String userName, @Query("password") String password) {

        return Observable.just(new LoginStatus(1000, "", null));
    }
}
