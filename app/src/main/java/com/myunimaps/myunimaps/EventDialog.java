package com.myunimaps.myunimaps;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tyler on 4/14/2015.
 */



public class EventDialog extends DialogFragment implements View.OnClickListener{

    private TextView eventInfo;
    private TextView eventTitle;
    Button close;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new GetAllEventsTask().execute(new EventConnector());
        View view = inflater.inflate(R.layout.event_dialog, null);
        this.eventInfo = (TextView) view.findViewById(R.id.eInfo);
        this.eventTitle = (TextView) view.findViewById(R.id.eTitle);

        close = (Button) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        return  view;
    }

    public void setTextToEvents(JSONArray jsonArray){


        for(int i=0; i<jsonArray.length();i++){

            String info = "";
            String title = "";
            JSONObject json;
            try{
                json = jsonArray.getJSONObject(i);
                title = title + json.getString("eName");
                info = info + json.getString("eInfo");
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            this.eventInfo.setText(info);
            this.eventTitle.setText(title);

        }


    }

    private class GetAllEventsTask extends AsyncTask<EventConnector,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(EventConnector... params){
            return params[0].GetAllEvents();
        }
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setTextToEvents(jsonArray);
        }
    }

    public void onClick(View view){

            dismiss();

    }
}
