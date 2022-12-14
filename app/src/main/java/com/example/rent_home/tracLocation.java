    package com.example.rent_home;
    import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.material.snackbar.Snackbar;
    import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
    public class tracLocation extends AppCompatActivity implements OnMapReadyCallback {
        //Here We have Used Mapview
        public MapView mapView;
        //Here The Latitudes And Longitudes Are Set
        Point dhaka = Point.fromLngLat(70.800705, 22.308155);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Mapbox.getInstance(this, getString(R.string.app_token));
            setContentView(R.layout.activity_trac_location);
            mapView = (MapView) findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }
        @Override
        public void onMapReady(@NonNull final MapboxMap mapboxMap) {
            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    CameraPosition position = new CameraPosition.Builder().target(new LatLng(dhaka.latitude(), dhaka.longitude()))
                        .zoom(13).tilt(13).build();
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                    mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                        LatLng source;
                        @Override
                        public boolean onMapClick(@NonNull LatLng point) {
                            mapboxMap.clear();
                            //Here the Marker Option is Set
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(point);
                            markerOptions.title("Location");
                            mapboxMap.addMarker(markerOptions);
                            MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                                .accessToken("pk.eyJ1IjoiYXlzaGFnYXppIiwiYSI6ImNrZXkxNXJxeDAwdXoyd28wNWpybmw1MDcifQ.cJYjWM_xg-wwPvpJEtAR5w")
                                .query(Point.fromLngLat(point.getLongitude(), point.getLatitude()))
                                .geocodingTypes(GeocodingCriteria.TYPE_POI)
                                .build();
                            reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {
                                @Override
                                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                                    List<CarmenFeature> results = response.body().features();
                                    if (results.size() > 0) {
                                    // CarmenFeature feature =results.get(0);
                                    CarmenFeature feature;
                                    // Log the first results Point.
                                    Point firstResultPoint = results.get(0).center();
                                    for (int i = 0; i < results.size(); i++) {
                                        feature = results.get(i);
                                        //    Toast.makeText(MainActivity.this, "" + results.get(i), Toast.LENGTH_LONG).show();
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),""+ feature.placeName(),
                                                Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                    Log.d("MyActivity", "onResponse: " + firstResultPoint.toString());
                                    } else {
                                    // No result for your request were found.
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Not Found",
                                                Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            });
                            return true;
                        }
                    });
                }
            });
        }
    }