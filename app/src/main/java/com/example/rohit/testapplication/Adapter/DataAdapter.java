package com.example.rohit.testapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rohit.testapplication.Activity.HomeActivity;
import com.example.rohit.testapplication.Activity.SplashActivity;
import com.example.rohit.testapplication.Model.UserProfile;
import com.example.rohit.testapplication.R;
import com.example.rohit.testapplication.SqlLite.DatabaseHelper;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private ArrayList<UserProfile> list;
    private Context context;
    private TranslateAnimation slideFromRight;
    DatabaseHelper databaseHelper;

    public DataAdapter(ArrayList<UserProfile> list, Context context) {
        this.list = list;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design_layout,parent,false);
        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtAge.setText(list.get(position).getAge());
        holder.txtGender.setText(list.get(position).getGender());
        holder.txtDob.setText(list.get(position).getDob());
        holder.txtLocation.setText(list.get(position).getLocation());
        Glide.with(context).load(list.get(position).getImgUrl())
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .dontTransform())
                .into(holder.imgViewProfile);

        holder.imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightDeleteAnimation(position);
                holder.cardLayout.startAnimation(slideFromRight);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void removeAt(int position) {
        databaseHelper.deleteOneData(list.get(position).getPhone());
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());

    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView txtName ,txtGender,txtDob,txtAge,txtLocation;
        ImageView imgViewProfile,imgViewDelete;
        CardView cardLayout;
        public DataViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtGender=itemView.findViewById(R.id.gender);
            txtDob=itemView.findViewById(R.id.dob);
            txtAge=itemView.findViewById(R.id.age);
            txtLocation=itemView.findViewById(R.id.location);
            imgViewProfile = itemView.findViewById(R.id.profile_image);
            imgViewDelete = itemView.findViewById(R.id.delete);
            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }

    private void rightDeleteAnimation(final int position){
        slideFromRight = new TranslateAnimation(0, SplashActivity.screenWidthPx, 0, 0);
        slideFromRight.setStartOffset(1600);
        slideFromRight.setDuration(500);
        slideFromRight.setInterpolator(new AccelerateDecelerateInterpolator());
        slideFromRight.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                removeAt(position);
            }
            @Override
            public void onAnimationStart(Animation animation) {
                super.onAnimationStart(animation);
            }
        });
    }
}
