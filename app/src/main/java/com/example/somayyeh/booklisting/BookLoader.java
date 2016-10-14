package com.example.somayyeh.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by somayyeh on 8/18/16.
 */
public class BookLoader extends AsyncTaskLoader<List<BookSpec>>{
    String mUrl;
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<BookSpec> loadInBackground() {
        if (mUrl == null)
        return null;
        try {
            return QueryUtils.fetchBookData(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
