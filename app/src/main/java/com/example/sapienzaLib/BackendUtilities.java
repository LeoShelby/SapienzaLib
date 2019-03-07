package com.example.sapienzaLib;

import android.content.Intent;
import android.net.http.RequestQueue;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BackendUtilities {

    static String TAG = "GoogleVerify";
    static String JWT = "";
    static GregorianCalendar EXPIREDATE;
    static OkHttpClient client = new OkHttpClient();

    public static String getBookByQuery(String q) throws InterruptedException {
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/book").newBuilder();
        urlBuilder.addQueryParameter("q", q);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
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

    public static void postBookByISBN(String q) throws InterruptedException {
        final String[] res = {""};
        Response response = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("isbn", q)
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/book").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                }
            }
        });
    }

    public static String verifyUser(String idToken) throws InterruptedException {

        final String[] res = {""};

        RequestBody requestBody = new FormBody.Builder()
                .add("idToken", idToken)
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/verifyUser").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error sending ID token to backend.", e);
                res[0] = null;
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String jwt = responseBody.string();
                    JWT = jwt;
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.add(Calendar.DATE, 1);
                    EXPIREDATE = gc;
                    res[0] = jwt;
                    countDownLatch.countDown();
                    Log.d(TAG, "Signed in succesfully to backend");
                }
            }
        });

        countDownLatch.await();
        return res[0];
    }

    public static void bookABook(String isbn){
        bookStuff("booking", isbn);
    }

    public static void wishaBook(String isbn){
        bookStuff("bookingwish", isbn);
    }

    public static Request getPopularBooks() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/booking/pop").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .build();

        return request;

    }

    public static Request getAllBookings() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/booking/all").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .build();

        return request;

    }

    public static Request getAllWishes() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/availablewish").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .build();

        return request;

    }

    public static Request getLastBook2() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/booking/last").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .build();

        return request;

    }


    public static Request getWished() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/booking/wished").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .build();

        return request;

    }

    public static String getBookByISBN(String q) throws InterruptedException {
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/book/"+q).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
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

    public static String getLastBook() throws InterruptedException{
        final String[] res = {""};
        Response response = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/booking/last").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
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
                    Log.d("valco", res[0]);
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return res[0];

    }

    public static void unbookABook(String isbn) {
        bookStuff("returnbook", isbn);
    }

    public static void unwishaBook(String isbn) {
        bookStuff("unwishbook", isbn);
    }

    public static void bookStuff(String what, String isbn){
        RequestBody requestBody = new FormBody.Builder()
                .add("isbn", isbn)
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sapienzalib.herokuapp.com/"+what).newBuilder();
        String url = urlBuilder.build().toString();

        Log.d("TOKEND", JWT);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("access-token", JWT)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error booking.", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {

                    Log.d(TAG, responseBody.string()    );
                }
            }
        });
    }
}
