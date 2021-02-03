package com.example.flickr_browser_java;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrData.OnDataAvailable,
RecyclerItemClickListener.OnRecyclerClickListener
{
    private static final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: returned:");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onPostResume: starts");
        super.onResume();
        GetFlickrData getFlickrData = new GetFlickrData(this,"https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
//    0    getFlickrData.executeOnSameThread("android, nougat");
        getFlickrData.execute("android, nougat");
        Log.d(TAG, "onResume: ends");
    }

    //    @Override
//    public void onDownloadComplete(String data, DownloadStatus status){
//        if(status == DownloadStatus.OK){
//            Log.d(TAG, "onDownloadComplete: data is " + data);
//        }else{
//            //downloading or processing failed
//            Log.e(TAG, "onDownloadComplete: failed with status " + status );
//        }
//    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDataAvailable: data is " + data);
            mFlickrRecyclerViewAdapter.loadNewData(data);
        }else{
            //downloading or processing failed
            Log.e(TAG, "onDataAvailable: failed with status " + status );
        }

        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
//        Toast.makeText(this, "Long tap at position " + position, Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(this, PhotoDetailActivity.class);
    intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));
    startActivity(intent);
    }
}