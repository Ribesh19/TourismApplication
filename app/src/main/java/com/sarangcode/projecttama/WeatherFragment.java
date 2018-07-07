package com.sarangcode.projecttama;




import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.LoaderManager;

import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        ForecastAdapter.ForecastAdapterOnClickHandler {

    public WeatherFragment() {
        // Required empty public constructor
    }
    private final String TAG = WeatherFragment.class.getSimpleName();
    Context myContext;

    public static final String[] MAIN_FORECAST_PROJECTION = {
            com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.COLUMN_DATE,
            com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
    };
    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_CONDITION_ID = 3;

    private static final int ID_FORECAST_LOADER = 44;


    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    private ProgressBar mLoadingIndicator;






    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview=inflater.inflate(R.layout.fragment_weather_layout, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0f);
        mRecyclerView = rootview.findViewById(R.id.recyclerview_forecast);
        mLoadingIndicator = rootview.findViewById(R.id.pb_loading_indicator);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new com.sarangcode.projecttama.ForecastAdapter(getContext(), this);
        mRecyclerView.setAdapter(mForecastAdapter);
        showLoading();

        getActivity().getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);

        getActivity().getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);

        com.sarangcode.projecttama.sync.SunshineSyncUtils.initialize(getContext());

        return rootview;

    }


    private void openPreferredLocationInMap() {
        double[] coords = com.sarangcode.projecttama.data.SunshinePreferences.getLocationCoordinates(getContext());
        String posLat = Double.toString(coords[0]);
        String posLong = Double.toString(coords[1]);
        Uri geoLocation = Uri.parse("geo:" + posLat + "," + posLong);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(myContext.getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

        switch (loaderId) {

            case ID_FORECAST_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri = com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
                String sortOrder = com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */
                String selection = com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();

                return new CursorLoader(getContext(),
                        forecastQueryUri,
                        MAIN_FORECAST_PROJECTION,
                        selection,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mForecastAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) showWeatherDataView();
        
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
         /*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         */
        mForecastAdapter.swapCursor(null);

    }

    

    

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    /**
     * This method is for responding to clicks from our list.
     *
     * @param date Normalized UTC time that represents the local date of the weather in GMT time.
     * @see com.sarangcode.projecttama.data.WeatherContract.WeatherEntry#COLUMN_DATE
     */
    @Override
    public void onClick(long date) {
        Intent weatherDetailIntent = new Intent(getActivity(), DetailActivity.class);
        Uri uriForDateClicked = com.sarangcode.projecttama.data.WeatherContract.WeatherEntry.buildWeatherUriWithDate(date);
        weatherDetailIntent.setData(uriForDateClicked);
        startActivity(weatherDetailIntent);
    }

    /**
     * This method will make the View for the weather data visible and hide the error message and
     * loading indicator.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, hide the loading indicator */
       mLoadingIndicator.setVisibility(View.INVISIBLE);
        /* Finally, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View and error
     * message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showLoading() {
        /* Then, hide the weather data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Finally, show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }



    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
     *
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */


}
