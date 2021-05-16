package com.desaco.fundroid.latitude_longitude;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.desaco.fundroid.R;
import com.desaco.fundroid.word_excel.poi_word.PoiWordMainActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
//import com.yanzhenjie.permission.runtime.Permission;

/**
 * @author dengwen
 * @date 2020/6/22.
 * <p>
 * com.desaco.fundroid.latitude_longitude.LocationActivity
 */
public class LocationActivity extends Activity {

    private double latitude = 0.0;
    private double longitude = 0.0;
    private int REQUEST_WRITE = 100;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // activity_location
        setContentView(R.layout.activity_location);
        //
        mTv = findViewById(R.id.show_ll);


        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    startLocate();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();

    }


    @SuppressWarnings("MissingPermission")
    private void startLocate() {
        Log.e("LocationActivity", "开始定位， startLocate()");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } else {
            LocationListener locationListener = new LocationListener() {
                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {
                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {
                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("LocationActivity", "Location changed : Lat: "
                                + location.getLatitude() + ",, Lng: "
                                + location.getLongitude());
                        mTv.setText("Location changed : Lat: " + location.getLatitude() + ",, Lng: " + location.getLongitude());
                    }
                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度

                mTv.setText("Location changed : Lat: "
                        + latitude + ",, Lng: "
                        + longitude);
            }
        }
    }
}
