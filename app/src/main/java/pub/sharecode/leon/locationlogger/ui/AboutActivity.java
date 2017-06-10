package pub.sharecode.leon.locationlogger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pub.sharecode.leon.locationlogger.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("关于");
    }
}
