package com.example.sapienzaLib;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BackendUtilities {

    static OkHttpClient client = new OkHttpClient();

    public static String getBookByQuery(String q) throws InterruptedException {
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://safe-forest-54835.herokuapp.com/book").newBuilder();
        urlBuilder.addQueryParameter("q", q);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                res[0] = null;
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) res[0] = null;

                    res[0] = responseBody.string();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return res[0];
    }
}
