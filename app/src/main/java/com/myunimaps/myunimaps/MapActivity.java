package com.myunimaps.myunimaps;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v7.widget.Toolbar;

public class MapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String[] mNavigationItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private double latitude;
    private double longitude;
    private String mLat, mLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        //      android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionbar(toolbar);

        Bundle gotLatLong = getIntent().getExtras();
        latitude = gotLatLong.getDouble("camplat");
        longitude = gotLatLong.getDouble("camplong");


        setUpMapIfNeeded();


        new GetAllMarkersTask().execute(new ApiConnector());
        new GetAllPolygonsTask().execute(new PolyConnector());

        mNavigationItems = getResources().getStringArray(R.array.navigation_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[3];
        drawerItem[0] = new ObjectDrawerItem("Choose Campus");
        drawerItem[1] = new ObjectDrawerItem("Help");
        drawerItem[2] = new ObjectDrawerItem("About Us");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


    }

    //private void setSupportActionbar(android.support.v7.widget.Toolbar toolbar) {
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global, menu);

        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.school_select) {
            startActivity(new Intent(this, CamSelTest.class));
        }
        return super.onOptionsItemSelected(item);
    }
*/

    public void showDialog(MenuItem item) {
        FragmentManager manager = getFragmentManager();
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(manager, "EventDialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera.
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       /* Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.71505, -95.5472))
                .title("AB1")
                .snippet("Home is where the motherboard is."));


        /* Make a loop to check if the point is the same as the first point. if no, keep going. */
//        mMap.animateCamera(yourLocation);
        LatLng myCoordinates = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 17);
        mMap.animateCamera(yourLocation);
        mMap.setMyLocationEnabled(true);

        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(30.71505, -95.5445),
                        new LatLng(30.7165, -95.5442),
                        new LatLng(30.7165, -95.5455),
                        new LatLng(30.71505, -95.5455),
                        new LatLng(30.71505, -95.5445))
                .strokeColor(Color.GREEN)
                .strokeWidth(6);

        Polygon polygon = mMap.addPolygon(rectOptions);
    }

    private class GetAllMarkersTask extends AsyncTask<ApiConnector, Long, JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].GetAllCustomers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setTextToMarkers(jsonArray);
        }
    }

    private class GetAllPolygonsTask extends AsyncTask<PolyConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(PolyConnector... params) {
            return params[0].GetAllPolys();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setTexttoPolygons(jsonArray);
        }
    }


    public void setTextToMarkers(JSONArray jsonArray) {


        for (int i = 0; i < jsonArray.length(); i++) {

            String slat = "";
            String slon = "";
            String sname = "";
            String sInfo = "";
            String sType = "";

            JSONObject json;

            try {
                json = jsonArray.getJSONObject(i);
                slat = slat + json.getDouble("mLat");
                slon = slon + json.getDouble("mLon");
                sname = sname + json.getString("mName");
                sInfo = sInfo + json.getString("mInfo");
                sType = sType + json.getString("mType");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Convert to Double
            double dlat = Double.parseDouble(slat);
            double dlon = Double.parseDouble(slon);
            int iType = Integer.parseInt(sType);

            Marker point;
            switch (iType) {
                case 1:
                    point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    break;
                case 2:
                    point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    break;
                case 3:
                    point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    break;
                case 4:
                    point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                    break;
                default:
                    point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo).icon(BitmapDescriptorFactory.fromResource(R.drawable.dotred)));

            }

//            Marker point = mMap.addMarker(new MarkerOptions().position(new LatLng(dlat, dlon)).title(sname).snippet(sInfo));
        }


    }

    public void setTexttoPolygons(JSONArray jsonArray) {

        /**CODING HAS STOPPED HERE!
         Figure out how to put polygon points on a map and distinguish them from other polygons
         **/

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Activity activity = null;

        switch (position) {
            //Campus Select
            case 0:
                //startActivity(new Intent(this, CamSelTest.class));
                NavUtils.navigateUpFromSameTask(this);
                mDrawerLayout.closeDrawer(mDrawerList);

                break;
            //Help
            case 1:
                startActivity(new Intent(this, Help.class));
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            //About Us
            case 2:
                startActivity(new Intent(this, AboutUs.class));
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            default:
                break;
        }

    }
}