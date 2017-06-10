package pub.sharecode.leon.locationlogger.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Timer;
import java.util.TimerTask;

import pub.sharecode.leon.locationlogger.R;
import pub.sharecode.leon.locationlogger.model.LocationData;
import pub.sharecode.leon.locationlogger.service.LocationService;
import pub.sharecode.leon.locationlogger.utils.TimeUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String BTN_STATUS = "btn_status";
    /**
     * Button
     */
    private Button btnStartOrStopService;
    private String TAG = this.getClass().getSimpleName();
    private int REQUEST_CODE_REQUEST_PERMISSION = 100;
    /**
     * 查看历史记录
     */
    private Button mBtnOpenHistoryActivity;
    /**
     * TextView
     */
    private TextView mTvLocation;
    /**
     * TextView
     */
    private TextView mTvTime;
    private Thread t;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        checkPermission();
    }

    private void initView(Bundle savedInstanceState) {
        btnStartOrStopService = (Button) findViewById(R.id.btn_start_or_stop_service);
        btnStartOrStopService.setOnClickListener(this);
        mBtnOpenHistoryActivity = (Button) findViewById(R.id.btn_open_history_activity);
        mBtnOpenHistoryActivity.setOnClickListener(this);
        mTvLocation = (TextView) findViewById(R.id.tv_location);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        if (savedInstanceState != null)
            btnStartOrStopService.setText(savedInstanceState.getString(BTN_STATUS));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_or_stop_service:
                if (btnStartOrStopService.getText().toString().equals("开始记录位置")) {
                    btnStartOrStopService.setText("停止记录位置");
                    startService(new Intent(MainActivity.this, LocationService.class));
                    timer = new Timer();
                    timer.schedule(new MyTask(), 0, 300);
                } else {
                    btnStartOrStopService.setText("开始记录位置");
                    stopService(new Intent(MainActivity.this, LocationService.class));
                    timer.cancel();
                }
                break;
            case R.id.btn_open_history_activity:
                Intent i = new Intent(MainActivity.this, LocationHistoryActivity.class);
                startActivity(i);
                break;
        }
    }

    /**
     * 检查定位权限
     */
    private void checkPermission() {
        Log.d(TAG, "checkPermission: ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_REQUEST_PERMISSION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(BTN_STATUS, btnStartOrStopService.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "已经获取到定位权限!", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LocationData locationData = DataSupport.findLast(LocationData.class);
                    if (locationData != null) {
                        mTvLocation.setText(locationData.getLatitude() + "," + locationData.getLongitude());
                        mTvTime.setText(TimeUtils.millis2String(locationData.getTime()));
                    }
                }
            });
        }
    }
}
