/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Within forecast_list_item.xml //////////////////////////////////////////////////////////////
    // TODO (COMPLETED) Add a layout for an item in the list called forecast_list_item.xml
    // TODO (COMPLETED) Make the root of the layout a vertical LinearLayout
    // TODO (COMPLETED) Set the width of the LinearLayout to match_parent and the height to wrap_content


    // TODO (COMPLETED) Add a TextView with an id @+id/tv_weather_data
    // TODO (COMPLETED) Set the text size to 22sp
    // TODO (COMPLETED) Make the width and height wrap_content
    // TODO (COMPLETED) Give the TextView 16dp of padding

    // TODO (COMPLETED) Add a View to the layout with a width of match_parent and a height of 1dp
    // TODO (COMPLETED) Set the background color to #dadada
    // TODO (COMPLETED) Set the left and right margins to 8dp
    // Within forecast_list_item.xml //////////////////////////////////////////////////////////////


    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
    // TODO (COMPLETED) Add a class file called ForecastAdapter
    // TODO (COMPLETED) Extend RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>

    // TODO (COMPLETED) Create a private string array called mWeatherData

    // TODO (COMPLETED) Create the default constructor (we will pass in parameters in a later lesson)



    // TODO (COMPLETED) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // TODO (COMPLETED) Extend RecyclerView.ViewHolder

    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
    // TODO (COMPLETED) Create a public final TextView variable called mWeatherTextView

    // TODO (COMPLETED) Create a constructor for this class that accepts a View as a parameter
    // TODO (COMPLETED) Call super(view) within the constructor for ForecastAdapterViewHolder
    // TODO (COMPLETED) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////


    // TODO (COMPLETED) Override onCreateViewHolder
    // TODO (COMPLETED) Within onCreateViewHolder, inflate the list item xml into a view
    // TODO (COMPLETED) Within onCreateViewHolder, return a new ForecastAdapterViewHolder with the above view passed in as a parameter

    // TODO (COMPLETED) Override onBindViewHolder
    // TODO (COMPLETED) Set the text of the TextView to the weather for this list item's position

    // TODO (COMPLETED) Override getItemCount
    // TODO (COMPLETED) Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null

    // TODO (COMPLETED) Create a setWeatherData method that saves the weatherData to mWeatherData
    // TODO (COMPLETED) After you save mWeatherData, call notifyDataSetChanged
    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////


    // TODO (COMPLETED) Delete mWeatherTextView


    // TODO (COMPLETED) Add a private RecyclerView variable called mRecyclerView
    // TODO (COMPLETED) Add a private ForecastAdapter variable called mForecastAdapter


    private RecyclerView mRecyclerView;

    private ForecastAdapter mForecastAdapter;
    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // TODO (COMPLETED) Delete the line where you get a reference to mWeatherTextView
        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */


        // TODO (COMPLETED) Use findViewById to get a reference to the RecyclerView

        mRecyclerView = findViewById(R.id.recyclerview_forecast);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (COMPLETED) Create layoutManager, a LinearLayoutManager with VERTICAL orientation and shouldReverseLayout == false

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        // TODO (COMPLETED) Set the layoutManager on mRecyclerView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Listede ki tüm öğelerin aynı boyutta olduğunu belli etmek için kullandık.
        // TODO (COMPLETED) Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);
        // TODO (COMPLETED) set mForecastAdapter equal to a new ForecastAdapter


        mForecastAdapter = new ForecastAdapter();
        // TODO (COMPLETED) Use mRecyclerView.setAdapter and pass in mForecastAdapter

        mRecyclerView.setAdapter(mForecastAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data. */
        loadWeatherData();
    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadWeatherData() {
        showWeatherDataView();

        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // TODO (COMPLETED) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // TODO (COMPLETED) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                showWeatherDataView();
                // TODO (COMPLETED) Instead of iterating through every string, use mForecastAdapter.setWeatherData and pass in the weather data

                mForecastAdapter.setWeatherData(weatherData);

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.forecast, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            // TODO (COMPLETED) Instead of setting the text to "", set the adapter to null before refreshing
            mForecastAdapter.setWeatherData(null);
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}