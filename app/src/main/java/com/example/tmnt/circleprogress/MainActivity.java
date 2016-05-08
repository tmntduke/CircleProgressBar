package com.example.tmnt.circleprogress;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tmnt.circleprogress.CircleRrogress;

public class MainActivity extends Activity {
    private CircleRrogress mCircleRrogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleRrogress = (CircleRrogress) findViewById(R.id.cricle);
        mCircleRrogress.update(5, 3000);
    }
}
