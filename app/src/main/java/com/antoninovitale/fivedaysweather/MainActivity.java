package com.antoninovitale.fivedaysweather;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antoninovitale.fivedaysweather.api.OpenWeatherMapApi;
import com.antoninovitale.fivedaysweather.api.model.CurrentWeather;
import com.antoninovitale.fivedaysweather.api.model.WeatherForecast;
import com.antoninovitale.fivedaysweather.ui.ApiMapper;
import com.antoninovitale.fivedaysweather.ui.WeatherInfoAdapter;
import com.antoninovitale.fivedaysweather.ui.model.CitySummaryModel;
import com.antoninovitale.fivedaysweather.ui.model.WeatherInfoModel;
import com.antoninovitale.fivedaysweather.util.AppUtil;
import com.antoninovitale.fivedaysweather.util.Constants;
import com.antoninovitale.fivedaysweather.util.DebugLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout
        .OnRefreshListener, GoogleApiClient.ConnectionCallbacks, LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 7755;

    private static final int REQUEST_LOCATION_PERMISSIONS = 8899;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 6655;

    private static final String FLICKR_API_KEY = ""; //TODO insert your API key

    private static final String FLICKR_API_SECRET = ""; //TODO insert your API shared secret

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fabSearch)
    FloatingActionButton fabSearch;

    @BindView(R.id.mainContent)
    LinearLayout mainContent;

    @BindView(R.id.ivWeatherIcon)
    WeatherIconView ivWeatherIcon;

    @BindView(R.id.tvCityName)
    TextView tvCityName;

    @BindView(R.id.tvTemperature)
    TextView tvTemperature;

    @BindView(R.id.tvWeatherInfo)
    TextView tvWeatherInfo;

    private Unbinder unbinder;

    private GoogleApiClient mGoogleApiClient;

    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    private double lastLatitude, lastLongitude;

    private boolean startLocationUpdatesPermissionCheck;

    private ImageLoaderTask imageLoaderTask;

    private WeatherInfoAdapter weatherInfoAdapter;

    private String lastUnitsFormatSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.purple, R.color.orange,
                R.color.green);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        weatherInfoAdapter = new WeatherInfoAdapter();
        recyclerView.setAdapter(weatherInfoAdapter);
        if (savedInstanceState != null) {
            lastLatitude = savedInstanceState.getDouble(Constants.LAST_LATITUDE);
            lastLongitude = savedInstanceState.getDouble(Constants.LAST_LONGITUDE);
            lastUnitsFormatSelected = savedInstanceState.getString(Constants
                    .LAST_UNITS_FORMAT_SELECTED);
        }
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useImperialUnits = settings.getBoolean(Constants.PREF_UNITS, false);
        MenuItem item2 = menu.findItem(R.id.action_imperial);
        item2.setChecked(useImperialUnits);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        item.setChecked(!item.isChecked());
        switch (item.getItemId()) {
            case R.id.action_imperial:
                editor.putBoolean(Constants.PREF_UNITS, item.isChecked());
                editor.apply();
                loadData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble(Constants.LAST_LATITUDE, lastLatitude);
        outState.putDouble(Constants.LAST_LONGITUDE, lastLongitude);
        outState.putString(Constants.LAST_UNITS_FORMAT_SELECTED, lastUnitsFormatSelected);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        if (!TextUtils.isEmpty(lastUnitsFormatSelected)) {
            String units = AppUtil.getUnitsFormat(this);
            if (!lastUnitsFormatSelected.equalsIgnoreCase(units)) {
                lastUnitsFormatSelected = units;
                loadData();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
        imageLoaderTask = null;
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(Constants.LOCATION_FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(Constants.LOCATION_DISPLACEMENT);
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            startLocationUpdatesPermissionCheck = true;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        DebugLog.log(TAG, "Periodic location updates started!");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            startLocationUpdatesPermissionCheck = false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        DebugLog.log(TAG, "Periodic location updates stopped!");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    /**
     * Method to toggle periodic location updates
     */
    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        } else {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }
    }

    @Override
    public void onRefresh() {
        getCurrentWeather(lastLatitude, lastLongitude);
        getWeatherForecast(lastLatitude, lastLongitude);
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        if (lastLatitude == 0 && lastLongitude == 0) {
            lastLatitude = Constants.DEFAULT_LAT;
            lastLongitude = Constants.DEFAULT_LON;
        }
        getCurrentWeather(lastLatitude, lastLongitude);
        getWeatherForecast(lastLatitude, lastLongitude);
        startImageLoaderTask();
    }

    private void getWeatherForecast(double lat, double lon) {
        lastUnitsFormatSelected = AppUtil.getUnitsFormat(this);
        Call<WeatherForecast> getWeatherForecastForLocation = OpenWeatherMapApi
                .getOpenWeatherMapApi().getWeatherForecastForLocation(String.valueOf(lat), String
                        .valueOf(lon), "40", lastUnitsFormatSelected, OpenWeatherMapApi
                        .OPEN_WEATHER_MAP_API_KEY);
        getWeatherForecastForLocation.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    WeatherForecast weatherForecast = response.body();
                    java.util.List<WeatherInfoModel> forecast = ApiMapper.createWeatherInfoModels
                            (MainActivity.this, weatherForecast);
                    weatherInfoAdapter.setItems(forecast);
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable throwable) {
                swipeRefreshLayout.setRefreshing(false);
                DebugLog.log(TAG, "onFailure", throwable);
            }
        });
    }

    private void getCurrentWeather(double lat, double lon) {
        lastUnitsFormatSelected = AppUtil.getUnitsFormat(this);
        Call<CurrentWeather> getCurrentWeatherForLocation = OpenWeatherMapApi.getOpenWeatherMapApi()
                .getCurrentWeatherForLocation(String.valueOf(lat), String.valueOf(lon),
                        lastUnitsFormatSelected,
                        OpenWeatherMapApi.OPEN_WEATHER_MAP_API_KEY);
        getCurrentWeatherForLocation.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    CurrentWeather currentWeather = response.body();
                    CitySummaryModel citySummaryModel = ApiMapper.createCitySummaryModel
                            (MainActivity.this, currentWeather);
                    showCitySummarySection(citySummaryModel);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable throwable) {
                swipeRefreshLayout.setRefreshing(false);
                DebugLog.log(TAG, "onFailure", throwable);
            }
        });
    }

    private void showCitySummarySection(CitySummaryModel citySummaryModel) {
        tvCityName.setText(citySummaryModel.cityName);
        ivWeatherIcon.setIconResource(citySummaryModel.weatherIcon);
        tvWeatherInfo.setText(citySummaryModel.weatherDescription);
        tvTemperature.setText(citySummaryModel.temperature);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        DebugLog.log(TAG, "onConnected");
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        DebugLog.log(TAG, "onConnectionSuspended");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        DebugLog.log(TAG, "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        DebugLog.log(TAG, "Location: " + location);
        lastLatitude = location.getLatitude();
        lastLongitude = location.getLongitude();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    if (startLocationUpdatesPermissionCheck) {
                        startLocationUpdates();
                    } else {
                        stopLocationUpdates();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng = place.getLatLng();
                lastLatitude = latLng.latitude;
                lastLongitude = latLng.longitude;
                getCurrentWeather(lastLatitude, lastLongitude);
                getWeatherForecast(lastLatitude, lastLongitude);
                startImageLoaderTask();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                if (status != null && !TextUtils.isEmpty(status.getStatusMessage())) {
                    DebugLog.log(TAG, status.getStatusMessage());
                }
            }
        }
    }

    @OnClick(R.id.fabSearch)
    public void onSearchPlacesClick() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter
                    (AutocompleteFilter.TYPE_FILTER_CITIES).build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            DebugLog.log(TAG, "GooglePlayServicesRepairableException", e);
        } catch (GooglePlayServicesNotAvailableException e) {
            DebugLog.log(TAG, "GooglePlayServicesNotAvailableException", e);
        }
    }

    private void startImageLoaderTask() {
        if (imageLoaderTask != null) {
            imageLoaderTask.cancel(true);
        }
        imageLoaderTask = new ImageLoaderTask();
        imageLoaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ImageLoaderTask extends AsyncTask<Void, Void, PhotoList> {

        @Override
        protected PhotoList doInBackground(Void... args) {
            try {
                Flickr f = new Flickr(FLICKR_API_KEY, FLICKR_API_SECRET);
                SearchParameters searchParams = new SearchParameters();
                searchParams.setAccuracy(11);
                searchParams.setLatitude(String.valueOf(lastLatitude));
                searchParams.setLongitude(String.valueOf(lastLongitude));
                searchParams.setTags(new String[]{"weather"});
                return f.getPhotosInterface().search(searchParams, 1, 1);
            } catch (IOException e) {
                DebugLog.log(TAG, "IOException", e);
            } catch (JSONException e) {
                DebugLog.log(TAG, "JSONException", e);
            } catch (FlickrException e) {
                DebugLog.log(TAG, "FlickrException", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(PhotoList photos) {
            if (photos != null && !photos.isEmpty()) {
                Glide.with(MainActivity.this).load(photos.get(0).getMedium800Url())
                        .into(new ViewTarget<LinearLayout, GlideDrawable>(mainContent) {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation
                                    anim) {
                                this.view.setBackground(resource);
                            }
                        });
            } else {
                mainContent.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color
                        .blue));
            }
        }
    }

}