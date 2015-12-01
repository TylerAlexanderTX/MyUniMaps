package com.myunimaps.myunimaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 4/23/2015.
 */
public class CamSelTest extends Activity implements SelAdpTest.ClickListener{
    private List<CampusItem> campusItemList = new ArrayList<CampusItem>();

    private RecyclerView cRecyclerView;
    private SelAdpTest selAda;
    private TextView campusName;
    private TextView campusInfo;
    public static String campusLat = "myLat";
    public static String campusLon = "myLong";
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_campus_list);
        new GetAllCampusTask().execute(new SelectionConnector());
        //getActionBar();

        cRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void itemCLicked(View view, int position) {
        //startActivity(new Intent(this, MapActivity.class));
//        Toast toast = Toast.makeText(mContext, (int) campusLat, Toast.LENGTH_SHORT);
//        toast.show();
//        intent.setData(Uri.parse("geo:30.6128,-96.3411"));
//        Bundle latLong = new Bundle();
//        campusLon = CampusItem.getcLon();
//        latLong.putString("camplat", campusLat);
  //      latLong.putString("camplong", campusLon);
   //     Intent intent = new Intent(CamSelTest.this,MapActivity.class);
     //   intent.putExtras(latLong);
        Intent intent = new Intent(this, MapActivity.class);



        startActivity(intent);
        //finish();
    }

    private class GetAllCampusTask extends AsyncTask<SelectionConnector,Long, JSONArray> {

        @Override
        protected JSONArray doInBackground(SelectionConnector... params){
            return params[0].GetAllCampuses();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){

            setTextToCampus(jsonArray);

            selAda = new SelAdpTest(CamSelTest.this, campusItemList);
            cRecyclerView.setAdapter(selAda);
        }


    }


    //Print JSON to Strings
    public void setTextToCampus(JSONArray jsonArray){

        try {
            if (null == campusItemList) {
                campusItemList = new ArrayList<CampusItem>();
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject campus;
                campus = jsonArray.getJSONObject(i);
                CampusItem item = new CampusItem();

                item.setTitle(campus.getString("cName"));
                item.setcCode(campus.getString("cCode"));
                item.setcLat(campus.getString("cLat"));
                item.setcLon(campus.getString("cLon"));

                //For when Dynamic images are added ->>> item.setThumbnail(getDrawable(thumbnail));
                campusItemList.add(item);

//                double cLat = Double.parseDouble(campus.getString("cLat"));
  //              double cLon = Double.parseDouble(campus.getString("cLon"));

//                campusLat = campus.getString("cLat");
  //              campusLon = campus.getString("cLon");

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

}



/*
        LatLng myCoordinates = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        mMap.animateCamera(yourLocation);

 */