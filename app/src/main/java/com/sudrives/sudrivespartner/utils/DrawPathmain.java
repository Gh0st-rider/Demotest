package com.sudrives.sudrivespartner.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sudrives.sudrivespartner.R;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user-5 on 6/4/18.
 */

public class DrawPathmain {
    GoogleMap mMap;
    Context mContext;
    LatLng origin;
    LatLng dest;
    public static List<Polyline> polylines = new ArrayList<Polyline>();

    private String from = "", to = "";

    public DrawPathmain(Context mContext, GoogleMap mMap, LatLng origin, LatLng dest, String from,  String to) {
        this.mMap = mMap;
        this.mContext = mContext;
        this.dest = dest;
        this.origin = origin;

        this.from = from;
        this.to = to;

        this.mMap.clear();

        // Getting URL to the Google Directions API
        String url = getUrl(origin, dest);

        FetchUrl FetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        try {
            if (url != null) {
                FetchUrl.execute(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));

        //move map camera
       /*  mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
         mMap.animateCamera(CameraUpdateFactory.zoomTo(8));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(origin, 11.0f);
        mMap.moveCamera(cameraUpdate);
     */ /*  CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(origin, 6.0f);
        mMap.moveCamera(cameraUpdate);*/

    }

    private String getUrl(LatLng origin, LatLng dest) {

        //if(from.equals("earnings")){

        // }


        mMap.addMarker(new MarkerOptions()
                .position(dest)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_flag)).title(to)).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(origin)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1)).title(from)).showInfoWindow();





        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin);
        builder.include(dest);

        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);



        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.setMinZoomPreference(12f);
        mMap.animateCamera(cameraUpdate);

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String server_key = "key=" + mContext.getResources().getString(R.string.direction_api_key);

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + server_key + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        Log.e("URL", "URL: " + url);

        // animateMarker(mMap,marker,dest,false);

        return url;
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                if (url[0] != null) {
                    data = downloadUrl(url[0]);
                }                // Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            // Invokes the thread for parsing the JSON data
            try {
                ParserTask parserTask = new ParserTask();
                if (result != null) {
                    parserTask.execute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            // Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap>>> {
        // Parsing the data in non-ui thread
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap>> routes = null;

            try {
                if (jsonData != null) {
                    jObject = new JSONObject(jsonData[0]);

                    Log.e("Json Response", jObject.toString());
                    // Log.d("ParserTask", jsonData[0].toString());
                    DataParser parser = new DataParser();
                    //  Log.d("ParserTask", parser.toString());

                    // Starts parsing data
                    routes = parser.parse(jObject);
                    // Log.d("ParserTask", "Executing routes");
                    //  Log.d("ParserTask", routes.toString());
                }
            } catch (Exception e) {
                // Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap>> result) {
            try {

                polylines.clear();

                Log.e("Result--", "" + result);
                ArrayList points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();
                if (result.size() != 0) {
                    for (int i = 0; i < result.size(); i++) {
                        points = new ArrayList();
                        lineOptions = new PolylineOptions();

                        List<HashMap> path = result.get(i);

                        for (int j = 0; j < path.size(); j++) {
                            HashMap point = path.get(j);

                            double lat = Double.parseDouble(String.valueOf(point.get("lat")));
                            double lng = Double.parseDouble(String.valueOf(point.get("lng")));
                            LatLng position = new LatLng(lat, lng);

                            points.add(position);
                        }

                        lineOptions.addAll(points);
                        lineOptions.width(8);
                        lineOptions.color(Color.BLACK);
                        lineOptions.geodesic(true);

                    }
                }
                // Drawing polyline in the Google Map for the i-th route
                if (lineOptions != null) {


                    polylines.add(mMap.addPolyline(lineOptions));


                    //mMap.addPolyline(lineOptions);
                } else {
                    Log.d("onPostExecute", "without Polylines drawn");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public static void animateMarker(final GoogleMap map, final Marker marker, final LatLng toPosition,
//                                     final boolean hideMarker) {
//        final Handler handler = new Handler();
//        final long start = SystemClock.uptimeMillis();
//        Projection proj = map.getProjection();
//        Point startPoint = proj.toScreenLocation(marker.getPosition());
//        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
//        final long duration = 500;
//
//        final LinearInterpolator interpolator = new LinearInterpolator();
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                long elapsed = SystemClock.uptimeMillis() - start;
//                float t = interpolator.getInterpolation((float) elapsed / duration);
//                double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
//                double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;
//
//                marker.setPosition(new LatLng(lat, lng));
//
//                if (t < 1.0) {
//                    // Post again 16ms later.
//                    handler.postDelayed(this, 16);
//                } else {
//                    if (hideMarker) {
//                        marker.setVisible(false);
//                    } else {
//                        marker.setVisible(true);
//                    }
//                }
//            }
//        });
//    }

    public static int dpAPixeles(int dp, Context contexto) {
        Resources r = contexto.getResources();
        int px = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());

        return px;
    }

    public static double degToRad(double deg){
        return deg * Math.PI / 180.0;
    }
    public static double radToDeg(double rad){
        rad = rad * (180.0 / Math.PI);
        if (rad < 0) rad = 360.0 + rad;
        return rad;
    }


}
///