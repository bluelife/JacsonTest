package com.example;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit.Retrofit;
import retrofit.mock.CallBehaviorAdapter;
import retrofit.mock.MockRetrofit;
import retrofit.mock.NetworkBehavior;
import retrofit.mock.RxJavaBehaviorAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by slomka.jin on 2015/10/30.
 */
public class ObservableTest {

    String name="rxdxr";
    String password="kkkfffddd";
    private ExecutorService bg;
    private LoginService loginService;
    private NetworkBehavior behavior;
    private LoginStatus status;

    @Before
    public void setUp() throws Exception {

        loginService=new LoginService();
        loginService.build();
        behavior=NetworkBehavior.create(new Random(2847));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldLoginOk() throws InterruptedException{


        Retrofit retrofit=loginService.getRetrofit();
        behavior.setDelay(200, TimeUnit.MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(0);
        NetworkBehavior.Adapter<?> adapter = RxJavaBehaviorAdapter.create();
        MockRetrofit mockRetrofit=new MockRetrofit(behavior,adapter);

        MockLoginApi mockLoginApi=new MockLoginApi(retrofit);

        LoginApi loginApi=mockRetrofit.create(LoginApi.class,mockLoginApi);

        final CountDownLatch latch = new CountDownLatch(1);
        Observable<LoginStatus> loginStatusObservable=loginApi.login(name, password);
        TestSubscriber<LoginStatus> testSubscriber=new TestSubscriber<>();

        loginStatusObservable.subscribe(new Subscriber<LoginStatus>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginStatus loginStatus) {
                status = loginStatus;
                latch.countDown();
            }
        });
        assertTrue(latch.await(1, SECONDS));
        //testSubscriber.assertNoValues();
        //testSubscriber.assertNoErrors();
        //List<LoginStatus> loginStatuses=testSubscriber.getOnNextEvents();
        LoginStatus loginStatus=new LoginStatus(1000,"",null);
        assertTrue(loginStatus.equals(status));
    }

    @Test
    public void shouldLoginFail(){

    }
}
