package com.example.somayyeh.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookSpec>> {

    private bookListAdapter bookAdapter;
    private List<BookSpec> bookItems;

    public final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=computers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        bookAdapter = new bookListAdapter(this, new ArrayList<BookSpec>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookAdapter);
        bookListView.setEmptyView(findViewById(R.id.emptyList));

        //check network connectivity
        ConnectivityManager myCon = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = myCon.getActiveNetworkInfo();
          if(netInfo != null && netInfo.isConnected()) {

              LoaderManager loaderManager = getLoaderManager();
              loaderManager.initLoader(0, null, this);
       /* bookAsyncTask task = new bookAsyncTask();
        task.execute(GOOGLE_BOOKS_URL);*/
        } else {
              ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
              progressBar.setVisibility(View.GONE);
              TextView emptyState = (TextView) findViewById(R.id.emptyList);
              emptyState.setText(R.string.no_network_data);
          }
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookSpec currentBookItem = (BookSpec) bookAdapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentBookItem.getSmallThumbnail()));
                startActivity(browserIntent);
            }
        });

    }

    @Override
    public Loader<List<BookSpec>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, GOOGLE_BOOKS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<BookSpec>> loader, List<BookSpec> data) {
        //ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        bookAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        TextView emptyState = (TextView) findViewById(R.id.emptyList);
        emptyState.setText(R.string.emptyData);

    }

    @Override
    public void onLoaderReset(Loader<List<BookSpec>> loader) {
        bookAdapter.clear();
    }
/*
    private class bookAsyncTask extends AsyncTask<String, Void, List<BookSpec>> {

        @Override
        protected List<BookSpec> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<BookSpec> result = null;
            try {
                try {
                    result = QueryUtils.fetchBookData(urls[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<BookSpec> data) {
            //ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.GONE);
            // Clear the adapter of previous earthquake data
            bookAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                bookAdapter.addAll(data);
            }
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            TextView emptyState = (TextView) findViewById(R.id.emptyList);
            emptyState.setText(R.string.emptyData);

        }
    }
    */
}
