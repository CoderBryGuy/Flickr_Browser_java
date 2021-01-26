package com.example.flickr_browser_java;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlickrData.OnDataAvailable {
    private static final String TAG = "MainActivity";


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
//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,nougat,sdk&tagmode=any&format=json&nojsoncallback=1");
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onPostResume: starts");
        super.onResume();
        GetFlickrData getFlickrData = new GetFlickrData(this,"https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        getFlickrData.executeOnSameThread("android, nougat");
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
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDataAvailable: data is " + data);
        }else{
            //downloading or processing failed
            Log.e(TAG, "onDataAvailable: failed with status " + status );
        }
    }
}