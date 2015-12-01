package com.myunimaps.myunimaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tyler on 4/23/2015.
 */
public class SelAdpTest extends RecyclerView.Adapter<SelAdpTest.MyViewHolder>{
    private List<CampusItem> campusItemList;
    private Context mContext;
    private LayoutInflater inflater;
    private ClickListener clickListener;
    private String lat;
    private String lon;

    public SelAdpTest(Context context, List<CampusItem> campusItemList) {

        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.campusItemList = campusItemList;
    }




    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.campus_rows, null);
        View v = inflater.inflate(R.layout.campus_rows, viewGroup, false);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }

    public void onBindViewHolder(MyViewHolder campusListRowHolder, int i) {
        CampusItem CampusItem = campusItemList.get(i);

       /* Picasso.with(mContext).load(String.valueOf(CampusItem.getThumbnail()))
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .into(campusListRowHolder.thumbnail);
        */
        campusListRowHolder.title.setText(CampusItem.getTitle());
        campusListRowHolder.cCode.setText(CampusItem.getcCOde());
        campusListRowHolder.cLat.setText(CampusItem.getcLat());
        campusListRowHolder.cLon.setText(CampusItem.getcLon());

    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    public int getItemCount() {
        return (null != campusItemList ? campusItemList.size() : 0);
    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView title;
        protected TextView cLat;
        protected TextView cLon;
        protected TextView cCode;


        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            //this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.campus_title);
            this.cLat = (TextView) view.findViewById(R.id.campus_Lat);
            this.cLon = (TextView) view.findViewById(R.id.campus_Lon);
            this.cCode = (TextView) view.findViewById(R.id.campus_code);


        }
        @Override

        public void onClick(View v){
            //mContext.startActivity(new Intent(mContext, MapActivity.class));
            Bundle latLong = new Bundle();
            double latitude = Double.parseDouble(cLat.getText().toString());
            double longitude = Double.parseDouble(cLon.getText().toString());
            latLong.putDouble("camplat", latitude);
            latLong.putDouble("camplong", longitude);
            Intent intent;
            intent = new Intent(mContext, MapActivity.class);
            intent.putExtras(latLong);
            mContext.startActivity(intent);



            if(clickListener!=null){
                clickListener.itemCLicked(v, getPosition());
            }

        }

    }


    public interface ClickListener{
        public void itemCLicked(View view, int position);
    }




}