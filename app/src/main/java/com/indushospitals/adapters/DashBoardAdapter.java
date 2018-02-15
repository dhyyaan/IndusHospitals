package com.indushospitals.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.indushospitals.R;
import com.indushospitals.customview.circulartextview.TextDrawable;
import com.indushospitals.fragments.ReferPatientDailogFragment;


/**
 * Created by think360 on 17/04/17.
 */
//https://github.com/amulyakhare/TextDrawable
public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

Activity ac;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);

            //circular text view
             image = (ImageView)view. findViewById(R.id.image_view);

        }
    }


    public DashBoardAdapter(Activity activity) {
    ac = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash_board_list_row, parent, false);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        FragmentManager fm = ((AppCompatActivity)ac).getSupportFragmentManager();
        ReferPatientDailogFragment dialogFragment = new ReferPatientDailogFragment();
        dialogFragment.show(fm, "Sample Fragment");
    }
});
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder(ac)
                .buildRoundRect("Ha", Color.RED,100); // radius in px
        holder. image.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}