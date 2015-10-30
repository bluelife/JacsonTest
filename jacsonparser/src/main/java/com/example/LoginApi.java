package com.example;

import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by slomka.jin on 2015/10/30.
 */
public interface LoginApi {
    String API_BASE_URL="http://passport.hupu.com/pc/login/";
    String LOGIN="member.action";

    @POST(LOGIN)
    Observable<LoginStatus> login(@Query("username") String userName,@Query("password") String password);
}
