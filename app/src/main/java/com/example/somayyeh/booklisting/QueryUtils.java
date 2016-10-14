package com.example.somayyeh.booklisting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by somayyeh on 8/15/16.
 */
public class QueryUtils
{
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static List<BookSpec> fetchBookData(String url) throws JSONException, IOException {
        List<BookSpec> booksList = null;
        try {
            URL murl = createUrl(url);
            String jsonResponse = makeHttpRequest(murl);
            booksList = extractBookData(jsonResponse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
return booksList;

    }

    public static URL createUrl(String url) throws MalformedURLException {
       return new URL(url);
     }
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<BookSpec> extractBookData(String jsonResponse) throws JSONException, IOException {
        List<BookSpec> booksList = new ArrayList<BookSpec>();
        String author = "";
      //  author.append("n");
//System.out.println("resp is: "+jsonResponse);
        JSONObject rootObj = new JSONObject(jsonResponse);
        JSONArray items = rootObj.getJSONArray("items");
        for (int i=0 ; i< items.length();i++) {
            JSONObject elements = items.getJSONObject(i);
           // String selfLink = elements.getString("selfLink");
            JSONObject volumeInfo = elements.getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");

            JSONArray authors = volumeInfo.optJSONArray("authors");
if (authors != null) {
    int j=0;
    author="";
    for (j = 0; j < authors.length()-1; j++)

        author  = author+authors.get(j)+",";
    author  = author+authors.get(j);
}
            else
{
    author = volumeInfo.getString("publisher");
}


            JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
            String infoLink = volumeInfo.getString("infoLink");


            String smallThumbnail="";
            Bitmap bmp = null;
            if (imageLinks != null) {
                smallThumbnail = imageLinks.optString("smallThumbnail");
                URL url = createUrl(smallThumbnail);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
            BookSpec bookItem = new BookSpec(title , author , bmp , infoLink);
            booksList.add(bookItem);
        }

            return booksList;


        }


}
