package com.example.flickr_browser_java;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mPhotoList = photoList;
        mContext = context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
            // called by layout manager when it wants to new data in an existing row
        Photo photoItem = mPhotoList.get(position);
        Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " --> " + position);
        Picasso.get()
                .load(photoItem.getImage())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: called");
        return ((mPhotoList != null) && (mPhotoList.size() != 0)) ? mPhotoList.size() : 0;
    }

    void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return ((mPhotoList != null) && (mPhotoList.size() !=0) ? mPhotoList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }

    }
}
