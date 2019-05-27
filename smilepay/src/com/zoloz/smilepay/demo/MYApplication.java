package com.zoloz.smilepay.demo;

import android.app.Application;
import com.alipay.zoloz.smile2pay.service.Zoloz;

import static com.zoloz.smilepay.demo.MerchantInfo.mockInfo;
/**
 * Created by bruce on 2018/6/15.
 */
public class MYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Zoloz zoloz = com.alipay.zoloz.smile2pay.service.Zoloz.getInstance(getApplicationContext());

    }

    @Override
    public void onTerminate() {

        super.onTerminate();
    }

}
