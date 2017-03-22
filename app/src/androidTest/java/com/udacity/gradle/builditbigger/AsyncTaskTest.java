package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;


import com.udacity.gradle.builditbigger.utils.AsyncJokeDownloader;
import com.udacity.gradle.builditbigger.utils.JokeDownloadListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by CS on 3/21/2017.
 */

public class AsyncTaskTest extends AndroidTestCase implements JokeDownloadListener {
    CountDownLatch signal;
    String joke = "";
    AsyncJokeDownloader downloader;

    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
        downloader = new AsyncJokeDownloader(this);
    }

    @UiThreadTest
    public void testAsync() throws InterruptedException {
        downloader.downloadJoke();
        signal.await(30, TimeUnit.SECONDS);

        assertTrue("Valid joke is returned", joke != null);
    }

    @Override
    public void downloadCompleted(String j) {
        joke = j;
        signal.countDown();
    }
}
