package com.example;

/**
 * Created by slomka.jin on 2015/10/30.
 */
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import retrofit.mock.MockRetrofit;
import retrofit.mock.NetworkBehavior;
import retrofit.mock.RxJavaBehaviorAdapter;
import rx.Observable;
import rx.Single;
import rx.Subscriber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public final class RxJavaBehaviorAdapterTest {
    interface DoWorkService {
        Observable<String> observableResponse();
        Single<String> singleResponse();
    }

    private final NetworkBehavior behavior = NetworkBehavior.create(new Random(2847));
    private DoWorkService service;

    @Before public void setUp() {
        DoWorkService mockService = new DoWorkService() {
            @Override public Observable<String> observableResponse() {
                return Observable.just("Hi!");
            }

            @Override public Single<String> singleResponse() {
                return Single.just("Hi!");
            }
        };

        NetworkBehavior.Adapter<?> adapter = RxJavaBehaviorAdapter.create();
        MockRetrofit mockRetrofit = new MockRetrofit(behavior, adapter);
        service = mockRetrofit.create(DoWorkService.class, mockService);
    }

    @Test public void observableFailureAfterDelay() throws InterruptedException {
        behavior.setDelay(100, MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(100);

        Observable<String> observable = service.observableResponse();

        final long startNanos = System.nanoTime();
        final AtomicLong tookMs = new AtomicLong();
        final AtomicReference<Throwable> failureRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                throw new AssertionError();
            }

            @Override
            public void onError(Throwable throwable) {
                tookMs.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
                failureRef.set(throwable);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
            }
        });
        //assertTrue(latch.await(1, SECONDS));

        //assertThat(failureRef.get()).isSameAs(behavior.failureException());
        //assertThat(tookMs.get()).isGreaterThanOrEqualTo(100);
    }

    @Test public void observableSuccessAfterDelay() throws InterruptedException {
        behavior.setDelay(100, MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(0);

        Observable<String> observable = service.observableResponse();

        final long startNanos = System.nanoTime();
        final AtomicLong tookMs = new AtomicLong();
        final AtomicReference<String> actual = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String value) {
                tookMs.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
                actual.set(value);
                System.out.println(value);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throw new AssertionError();
            }

            @Override
            public void onCompleted() {
            }
        });
        assertTrue(latch.await(1, SECONDS));
        //Assert.assertTrue(latch.await(1,SECONDS));
        //Assert.assertTrue(actual.get().equals("Hi!"));
        //assertThat(actual.get()).isEqualTo("Hi!");
        //assertThat(tookMs.get()).isGreaterThanOrEqualTo(100);
    }

    @Test public void singleFailureAfterDelay() throws InterruptedException {
        behavior.setDelay(100, MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(100);

        Single<String> observable = service.singleResponse();

        final long startNanos = System.nanoTime();
        final AtomicLong tookMs = new AtomicLong();
        final AtomicReference<Throwable> failureRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                throw new AssertionError();
            }

            @Override
            public void onError(Throwable throwable) {
                tookMs.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
                failureRef.set(throwable);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
            }
        });
        //assertTrue(latch.await(1, SECONDS));

        //assertThat(failureRef.get()).isSameAs(behavior.failureException());
        //assertThat(tookMs.get()).isGreaterThanOrEqualTo(100);
    }

    @Test public void singleSuccessAfterDelay() throws InterruptedException {
        behavior.setDelay(100, MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(0);

        Single<String> observable = service.singleResponse();

        final long startNanos = System.nanoTime();
        final AtomicLong tookMs = new AtomicLong();
        final AtomicReference<String> actual = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String value) {
                tookMs.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
                actual.set(value);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throw new AssertionError();
            }

            @Override
            public void onCompleted() {
            }
        });
        //assertTrue(latch.await(1, SECONDS));

        //assertThat(actual.get()).isEqualTo("Hi!");
        //assertThat(tookMs.get()).isGreaterThanOrEqualTo(100);
    }
}