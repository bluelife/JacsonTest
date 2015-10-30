package com.example;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by slomka.jin on 2015/10/30.
 */
public class ForumLogin {

    public void login(String name,String password){
        LoginApi loginApi=new LoginService().build();
        Observable<LoginStatus> loginStatusObservable=loginApi.login(name, password);
        loginStatusObservable.observeOn(Schedulers.io())
                .subscribe(new Subscriber<LoginStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginStatus loginStatus) {

                    }
                });
    }



}
