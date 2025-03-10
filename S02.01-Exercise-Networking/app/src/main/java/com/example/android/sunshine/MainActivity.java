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
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        // Completed (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.
        /*
         * This String array contains dummy weather data. Later in the course, we're going to get
         * real weather data. For now, we want to get something on the screen as quickly as
         * possible, so we'll display this dummy data.
         */

        // Completed (3) Delete the for loop that populates the TextView with dummy data
        /*
         * Iterate through the array and append the Strings to the TextView. The reason why we add
         * the "\n\n\n" after the String is to give visual separation between each String in the
         * TextView. Later, we'll learn about a better way to display lists of data.
         */

        loadWeatherData();
        // Completed (9) Call loadWeatherData to perform the network request to get the weather
    }

    // Completed (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData

    public void loadWeatherData()
    {
        String userPreferredLocation= SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(userPreferredLocation);
    }

    // Completed (5) Create a class that extends AsyncTask to perform network requests
     public class FetchWeatherTask extends AsyncTask<String,Void,String[]>
    {
        @Override
        protected String[] doInBackground(String... urls)
        {
            String weatherData=null;
            URL weatherDataURL=NetworkUtils.buildUrl(urls[0]);
            try{
                weatherData= NetworkUtils.getResponseFromHttpUrl(weatherDataURL);
                String[] weatherDataJSON= OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this,weatherData);
                return weatherDataJSON;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(String weatherData[])
        {
            if(weatherData!=null)
            {
               for (String weatherDatum:weatherData)
                   mWeatherTextView.append(weatherDatum+"\n\n\n");
            }
        }
    }
    // Completed (6) Override the doInBackground method to perform your network requests
    // Completed (7) Override the onPostExecute method to display the results of the network request
}