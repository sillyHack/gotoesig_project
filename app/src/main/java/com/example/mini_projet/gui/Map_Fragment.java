package com.example.mini_projet.gui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mini_projet.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.projection.Point;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.google.maps.android.geojson.GeoJsonPoint;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map_Fragment extends Fragment {

    private GoogleMap mMap;
    public LatLng latLng = null;
    public String latDpt = "";
    public String lonDpt = "";
    public String latArr = "";
    public String lonArr = "";
    MarkerOptions origin, destination;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);


        Bundle bundle = getArguments();
        latDpt = bundle.getString("latDpt");
        lonDpt = bundle.getString("lonDpt");
        latArr = bundle.getString("latArr");
        lonArr = bundle.getString("lonArr");

        origin = new MarkerOptions().position(new LatLng(Double.valueOf(latDpt), Double.valueOf(lonDpt))).title("Depart").snippet("origin");
        destination = new MarkerOptions().position(new LatLng(Double.valueOf(latArr), Double.valueOf(lonArr))).title("Arrivee").snippet("destination");


        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin.getPosition(), destination.getPosition());

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);


        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                //LatLng esigelec = new LatLng(49.383430, 1.0773341);
                        /*googleMap.addMarker(new MarkerOptions().position(latLng).title("ESIGELEC"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));*/


                /*String json = "{\"type\":\"FeatureCollection\",\"features\":[{\"bbox\":[8.681423,49.414599,8.69198,49.420514],\"type\":\"Feature\",\"properties\":{\"segments\":[{\"distance\":1603.8,\"duration\":268.7,\"steps\":[{\"distance\":1.9,\"duration\":0.5,\"type\":11,\"instruction\":\"Head west on Gerhart-Hauptmann-Straße\",\"name\":\"Gerhart-Hauptmann-Straße\",\"way_points\":[0,1]},{\"distance\":314,\"duration\":75.4,\"type\":3,\"instruction\":\"Turn sharp right onto Wielandtstraße\",\"name\":\"Wielandtstraße\",\"way_points\":[1,11]},{\"distance\":737.6,\"duration\":106.2,\"type\":1,\"instruction\":\"Turn right onto Mönchhofstraße\",\"name\":\"Mönchhofstraße\",\"way_points\":[11,39]},{\"distance\":264.3,\"duration\":41.4,\"type\":0,\"instruction\":\"Turn left onto Handschuhsheimer Landstraße, B 3\",\"name\":\"Handschuhsheimer Landstraße, B 3\",\"way_points\":[39,57]},{\"distance\":155.3,\"duration\":14,\"type\":5,\"instruction\":\"Turn slight right onto Handschuhsheimer Landstraße, B 3\",\"name\":\"Handschuhsheimer Landstraße, B 3\",\"way_points\":[57,61]},{\"distance\":130.8,\"duration\":31.4,\"type\":0,\"instruction\":\"Turn left onto Roonstraße\",\"name\":\"Roonstraße\",\"way_points\":[61,64]},{\"distance\":0,\"duration\":0,\"type\":10,\"instruction\":\"Arrive at Roonstraße, straight ahead\",\"name\":\"-\",\"way_points\":[64,64]}]}],\"summary\":{\"distance\":1603.8,\"duration\":268.7},\"way_points\":[0,64]},\"geometry\":{\"coordinates\":[[8.681496,49.414601],[8.68147,49.414599],[8.681488,49.41465],[8.681423,49.415698],[8.681423,49.415746],[8.681427,49.415802],[8.681641,49.416539],[8.681656,49.41659],[8.681672,49.416646],[8.681825,49.417081],[8.681875,49.417287],[8.681881,49.417392],[8.682035,49.417389],[8.682107,49.41739],[8.682461,49.417389],[8.682563,49.417388],[8.682676,49.417387],[8.682781,49.417388],[8.683379,49.41738],[8.683595,49.417372],[8.683709,49.417368],[8.685294,49.417365],[8.685359,49.417365],[8.685442,49.417365],[8.685713,49.41737],[8.686407,49.417365],[8.686717,49.417366],[8.687376,49.417353],[8.687466,49.417351],[8.687547,49.417349],[8.688256,49.417361],[8.688802,49.417381],[8.690001,49.417413],[8.690275,49.417425],[8.690344,49.417424],[8.690434,49.417417],[8.691467,49.417155],[8.691735,49.417102],[8.691957,49.417117],[8.69198,49.417121],[8.691941,49.41722],[8.691931,49.417235],[8.691879,49.417306],[8.691817,49.417369],[8.691427,49.417726],[8.691072,49.418051],[8.690968,49.418158],[8.690936,49.418188],[8.690939,49.418208],[8.690939,49.418296],[8.69092,49.418378],[8.690912,49.418411],[8.69067,49.418981],[8.690664,49.418992],[8.690614,49.419093],[8.690547,49.419174],[8.690479,49.419204],[8.690446,49.419218],[8.690275,49.419577],[8.690123,49.419833],[8.689854,49.420216],[8.689652,49.420514],[8.68963,49.42051],[8.688601,49.420393],[8.687872,49.420318]],\"type\":\"LineString\"}}],\"bbox\":[8.681423,49.414599,8.69198,49.420514],\"metadata\":{\"attribution\":\"openrouteservice.org | OpenStreetMap contributors\",\"service\":\"routing\",\"timestamp\":1641351573589,\"query\":{\"coordinates\":[[8.681495,49.41461],[8.687872,49.420318]],\"profile\":\"driving-car\",\"format\":\"json\"},\"engine\":{\"version\":\"6.6.3\",\"build_date\":\"2021-12-16T11:22:41Z\",\"graph_date\":\"2021-12-30T00:30:23Z\"}}}";
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.d("testJSON", jsonObject.getString("type"));
                    //GeoJsonLayer layer = new GeoJsonLayer(googleMap, jsonObject);
                    //layer.addLayerToMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


                /*OkHttpClient client = new OkHttpClient();

                Request requestGeoJson = new Request.Builder()
                        .url("https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248c8d7034edd1140f3a0751d6d3a6e7c12&start=8.681495,49.41461&end=8.687872,49.420318")
                        .build();*/


                /*client.newCall(requestGeoJson).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        //Log.d("GEOJSON",response.body().string());

                        String webContent = response.body().string();
                        if(!response.isSuccessful()){
                            Log.e("error", "api error occured while getting the geoJSON!");
                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(webContent);
                                //Log.d("testJSON", jsonObject.getString("type"));
                                GeoJsonLayer layer = new GeoJsonLayer(googleMap, jsonObject);
                                layer.addLayerToMap();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });*/

                /*LatLng origin = new LatLng(49.41461, 8.681495);
                LatLng dest = new LatLng(49.420318, 8.687872);*/

                // Getting URL to the Google Directions API
                /*String url = getDirectionsUrl(origin, dest);

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);*/
                mMap.addMarker(origin);
                mMap.addMarker(destination);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin.getPosition(), 10));

            }
        });

        return view;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map
            if (points.size() != 0)
                mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        //setting transportation mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest  + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyB5ss9qeW94g8fNfdO8mPHIFVDT8NPpqUE";

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}