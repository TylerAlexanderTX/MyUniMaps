package com.myunimaps.myunimaps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tyler on 4/20/2015.
 */
public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorViewHolder>{

    private List<SelectionConnector> campusList;

    public SelectorAdapter(List<SelectionConnector> campusList){
        this.campusList = campusList;

    }

    @Override
    public int getItemCount()
    {
        return campusList.size();
    }

    @Override
    public void onBindViewHolder(SelectorViewHolder selectorViewHolder, int i){
        SelectionConnector sc = campusList.get(i);
//        selectorViewHolder.sTitle.setText(sc.name);

    }

    @Override
    public SelectorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selection_cards, viewGroup, false);

        return new SelectorViewHolder(itemView);
    }



    public static class SelectorViewHolder extends RecyclerView.ViewHolder{

        protected TextView sTitle;
        protected ImageView sImage;

        public SelectorViewHolder(View v){
            super(v);
            sTitle = (TextView) v.findViewById(R.id.sTitle);
            sImage = (ImageView) v.findViewById(R.id.sImage);

        }

    }

}
