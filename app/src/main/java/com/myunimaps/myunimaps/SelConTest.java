package com.myunimaps.myunimaps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Tyler on 4/23/2015.
 */
public class SelConTest extends RecyclerView.ViewHolder implements View.OnClickListener{
    //protected ImageView thumbnail;
    protected TextView title;
    protected TextView cLat;
    protected TextView cLon;
    protected TextView cCode;



    public SelConTest(View view) {
        super(view);
        //this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.campus_title);
        this.cLat = (TextView) view.findViewById(R.id.campus_Lat);
        this.cLon = (TextView) view.findViewById(R.id.campus_Lon);
        this.cCode = (TextView) view.findViewById(R.id.campus_code);
        view.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
    }



}

