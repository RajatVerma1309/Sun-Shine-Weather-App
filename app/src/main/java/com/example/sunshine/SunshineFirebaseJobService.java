package com.example.sunshine;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;




public class SunshineFirebaseJobService extends JobService {


    private AsyncTask<Void, Void, Void> mFetchWeatherTask;

   
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchWeatherTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                SunshineSyncTask.syncWeather(context);
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onPostExecute(Void aVoid) {
                //  COMPLETED (6) Once the weather data is sync'd, call jobFinished with the appropriate arguements
                jobFinished(jobParameters, false);
            }
        };

        mFetchWeatherTask.execute();
        return true;
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchWeatherTask != null) {
            mFetchWeatherTask.cancel(true);
        }
        return true;
    }
}