package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private String mForecast;
    private TextView mWeatherDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO (COMPLETED) Display the weather forecast that was passed from MainActivity

        mWeatherDisplay = (TextView) findViewById(R.id.tv_weather_data);
        Intent gelenIntent = getIntent();
        if (gelenIntent != null) {

            if (gelenIntent.hasExtra(Intent.EXTRA_TEXT)) {

                mForecast = gelenIntent.getStringExtra(Intent.EXTRA_TEXT);
                mWeatherDisplay.setText(mForecast);
            }

        }


    }
}