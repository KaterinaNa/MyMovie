package com.example.mymovie.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private final static String API_KEY = "55234da7901571c2cbfa66d4b30ae6f2";
    private final static String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private String AuthAPI = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTIzNGRhNzkwMTU3MWMyY2JmYTY2ZDRiMzBhZTZmMiIsInN1YiI6IjYyMTlmNDgxMGU1OTdiMDAxYmEyYmJjZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KDU1D7UqB6l_MRkMEgPkiQVVbfqUW-9IRUpYm1pKdxw";

    private final static String PARAMS_API_KEY = "api_key";
    private final static String PARAMS_LANGUAGE = "language";
    private final static String PARAMS_SORT_BY = "sort_by";
    private final static String PARAMS_PAGE = "page";

    private final static String LANGUAGE_VALUE = "ru-RU";
    private final static  String SORT_BY_POPULAR = "popularty.desc";
    private final static String SORT_BY_TOP_RATED = "vote_average.desc";

    public static int POPULARTY = 0;
    public static int  TOP_RATED = 1;

    private static URL buildURL(int sortBy, int page) {
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARTY) {
            methodOfSort = PARAMS_SORT_BY;
        }
        else {methodOfSort = SORT_BY_TOP_RATED;}

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, SORT_BY_POPULAR)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
           result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetwork (int sortBy, int page) {
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask <URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }
}

