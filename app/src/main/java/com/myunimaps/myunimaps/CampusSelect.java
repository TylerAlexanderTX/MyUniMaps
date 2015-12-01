package com.myunimaps.myunimaps;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 4/20/2015.
 */
public class CampusSelect extends Activity {


    private TextView campusName;
    private TextView campusInfo;
    private TextView cID;

    @Override
    protected void  onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_main);
        RecyclerView recList = (RecyclerView)findViewById(R.id.cardList);
        recList.hasFixedSize();
        GridLayoutManager  glm = new GridLayoutManager(this, 2);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        recList.setLayoutManager(glm);

//        SelectorAdapter sc = new SelectorAdapter(createList (30));
  //      recList.setAdapter(sc);

//        recList.setOnClickListener();
/*
       recList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
           @Override
           public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return true;
           }

           @Override
           public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);

           }
       });
*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    public void showDialog(MenuItem item)
    {
        FragmentManager manager = getFragmentManager();
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(manager,"EventDialog");
    }

    // Get Data from SelectionConnector
    private class GetAllCampusTask extends AsyncTask<ApiConnector,Long, JSONArray> {

        @Override
        protected JSONArray doInBackground(ApiConnector... params){
            return params[0].GetAllCustomers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){

            setTextToCampus(jsonArray);
        }
    }

    //Print JSON to Strings
    public void setTextToCampus(JSONArray jsonArray){


        for(int i=0; i<jsonArray.length();i++){

            String cName = "";
            String cInfo = "";
            String cCode= "";

            JSONObject json;

            try{
                json = jsonArray.getJSONObject(i);
                cName = cName + json.getString("cName");
                cInfo = cInfo + json.getString("cInfo");
                cCode = cCode + json.getDouble("cCode");
            }
            catch (JSONException e){
                e.printStackTrace();
            }

 //           this.eventInfo.setText(info);
   //         this.eventTitle.setText(title);

            this.campusInfo.setText(cInfo);
            this.campusName.setText(cName);
            this.cID.setText(cCode);

        }


    }



    private List<CampusSelect> createList(TextView cID){

//        double cCode = Double.parseDouble(cID);

        List<CampusSelect> result = new ArrayList<CampusSelect>();
        for(int i= 1; i<= 10; i++){
            CampusSelect cs = new CampusSelect();
  //          cs.campusName = campusName + "i";

            result.add(cs);
        }

        return result;
    }






}
