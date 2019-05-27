package com.test.testsmilepaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zoloz.smilepay.demo.PayTest;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by bruce on 2018/6/15.
 */
public class MainActivity extends AppCompatActivity {
    com.zoloz.smilepay.demo.PayTest payTest;
    private boolean isAirplay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        payTest=new PayTest(MainActivity.this);
        Button btn=findViewById(R.id.smile2pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTest.smilePay(0.01);
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        payTest.close();
    }
}
